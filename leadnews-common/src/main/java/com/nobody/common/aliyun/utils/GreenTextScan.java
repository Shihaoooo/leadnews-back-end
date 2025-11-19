package com.nobody.common.aliyun.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.green.model.v20180509.TextScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import java.util.HashMap;

public class GreenTextScan {

    @Getter
    private final GreenProperties greenProperties;
    public final String accessKeyId;
    private final String accessKeySecret;
    private final String endpoint;

    public GreenTextScan(GreenProperties greenProperties){
        this.greenProperties = greenProperties;
        accessKeyId = greenProperties.getAccessKeyId();
        accessKeySecret = greenProperties.getAccessKeySecret();
        endpoint = greenProperties.getEndpoint();
    }

    public Map<String, Object> textScan(String content) {
        // 初始化返回结果
        Map<String, Object> resultMap = new HashMap<>(2);

        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-shanghai",
                accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-shanghai", "Green", endpoint);
        IAcsClient client = new DefaultAcsClient(profile);
        TextScanRequest textScanRequest = new TextScanRequest();
        textScanRequest.setAcceptFormat(FormatType.JSON);
        textScanRequest.setHttpContentType(FormatType.JSON);
        textScanRequest.setMethod(com.aliyuncs.http.MethodType.POST);
        textScanRequest.setEncoding("UTF-8");
        textScanRequest.setRegionId("cn-shanghai");

        List<Map<String, Object>> tasks = new ArrayList<>();
        Map<String, Object> task1 = new LinkedHashMap<>();
        task1.put("dataId", UUID.randomUUID().toString());
        task1.put("content", content);
        tasks.add(task1);

        JSONObject data = new JSONObject();
        data.put("scenes", List.of("antispam"));
        data.put("tasks", tasks);

        textScanRequest.setHttpContent(data.toJSONString().getBytes(), "UTF-8", FormatType.JSON);
        textScanRequest.setConnectTimeout(3000);
        textScanRequest.setReadTimeout(6000);

        try {
            HttpResponse httpResponse = client.doAction(textScanRequest);

            // 接口调用失败（非200状态码）
            if (!httpResponse.isSuccess()) {
                String errorMsg = "审核接口调用失败，状态码：" + httpResponse.getStatus();
                resultMap.put("success", false);
                resultMap.put("msg", errorMsg);
                System.out.println(errorMsg);
                return resultMap;
            }

            JSONObject scrResponse = JSON.parseObject(new String(httpResponse.getHttpContent(), StandardCharsets.UTF_8));
            System.out.println("审核接口返回：" + JSON.toJSONString(scrResponse));

            // 审核服务处理失败（code非200）
            if (200 != scrResponse.getInteger("code")) {
                String errorMsg = "审核处理失败，错误码：" + scrResponse.getInteger("code") + "，信息：" + scrResponse.getString("msg");
                resultMap.put("success", false);
                resultMap.put("msg", errorMsg);
                return resultMap;
            }

            JSONArray taskResults = scrResponse.getJSONArray("data");
            // 记录所有违规信息
            StringBuilder blockMsg = new StringBuilder();

            for (Object taskResult : taskResults) {
                JSONObject taskJson = (JSONObject) taskResult;
                // 单个任务处理失败
                if (200 != taskJson.getInteger("code")) {
                    String errorMsg = "单任务处理失败，错误码：" + taskJson.getInteger("code");
                    resultMap.put("success", false);
                    resultMap.put("msg", errorMsg);
                    return resultMap;
                }

                // 解析场景审核结果
                JSONArray sceneResults = taskJson.getJSONArray("results");
                for (Object sceneResult : sceneResults) {
                    JSONObject sceneJson = (JSONObject) sceneResult;
                    String suggestion = sceneJson.getString("suggestion");
                    String label = sceneJson.getString("label"); // 违规标签（如"porn"、"politics"等）

                    // 命中违规内容（block）
                    if ("block".equals(suggestion)) {
                        if (!blockMsg.isEmpty()) {
                            blockMsg.append("；");
                        }
                        blockMsg.append("命中违规类型：").append(label);
                    }
                }
            }

            // 最终判断：是否有违规
            if (!blockMsg.isEmpty()) {
                resultMap.put("success", false);
                resultMap.put("msg", blockMsg.toString());
            } else {
                resultMap.put("success", true);
                resultMap.put("msg", null); // 审核通过，msg为null
            }

        } catch (Exception e) {
            // 捕获异常（如网络错误、解析错误等）
            String errorMsg = "审核过程发生异常：" + e.getMessage();
            resultMap.put("success", false);
            resultMap.put("msg", errorMsg);
            e.printStackTrace();
        }

        return resultMap;
    }


}