package com.nobody.common.aliyun.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.green.model.v20180509.ImageSyncScanRequest;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.HttpResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.commons.codec.binary.Base64;

import java.util.*;

public class GreenImageScan {
    public GreenImageScan(GreenProperties greenProperties) {
        this.greenProperties = greenProperties;
        accessKeyId = greenProperties.getAccessKeyId();
        accessKeySecret = greenProperties.getAccessKeySecret();
        endpoint = greenProperties.getEndpoint();
    }

    private final GreenProperties greenProperties;
    private final String accessKeyId;
    private final String accessKeySecret;
    private final String endpoint;


    /**
     * 批量图片审核
     * @param imageBytesList 多张图片的字节数组列表
     * @return Map 包含两个键：
     *         - success: boolean 所有图片均审核通过则为true，否则为false
     *         - msg: String 审核不通过时的汇总原因（所有图片均通过时为null）
     */
    public Map<String, Object> imageScan(List<byte[]> imageBytesList) {
        // 初始化返回结果
        Map<String, Object> resultMap = new HashMap<>(2);
        // 汇总所有错误信息（单张图片的错误或违规）
        StringBuilder allErrorMsg = new StringBuilder();

        // 校验入参：图片列表为空时直接返回错误
        if (imageBytesList == null || imageBytesList.isEmpty()) {
            resultMap.put("success", false);
            resultMap.put("msg", "图片列表不能为空");
            return resultMap;
        }

        try {
            // 初始化阿里云客户端
            DefaultProfile profile = DefaultProfile.getProfile(
                    "cn-shanghai",
                    accessKeyId,
                    accessKeySecret);
            DefaultProfile.addEndpoint("cn-shanghai", "Green", endpoint);
            IAcsClient client = new DefaultAcsClient(profile);

            // 构建请求参数
            ImageSyncScanRequest imageSyncScanRequest = new ImageSyncScanRequest();
            imageSyncScanRequest.setAcceptFormat(FormatType.JSON);
            imageSyncScanRequest.setMethod(MethodType.POST);
            imageSyncScanRequest.setEncoding("utf-8");
            imageSyncScanRequest.setProtocol(ProtocolType.HTTP);

            JSONObject httpBody = new JSONObject();
            httpBody.put("scenes", List.of("porn")); // 检测场景（可扩展多场景）

            // 为每张图片创建一个任务
            List<JSONObject> tasks = new ArrayList<>();
            for (int i = 0; i < imageBytesList.size(); i++) {
                byte[] imageBytes = imageBytesList.get(i);
                // 跳过空字节数组（避免无效请求）
                if (imageBytes == null || imageBytes.length == 0) {
                    allErrorMsg.append("第").append(i + 1).append("张图片为空；");
                    continue;
                }
                JSONObject task = new JSONObject();
                task.put("dataId", UUID.randomUUID().toString()); // 每张图片唯一标识
                task.put("content", Base64.encodeBase64String(imageBytes)); // Base64编码
                task.put("time", new Date());
                tasks.add(task);
            }

            // 若所有图片均为空，直接返回错误
            if (tasks.isEmpty()) {
                resultMap.put("success", false);
                resultMap.put("msg", allErrorMsg.toString());
                return resultMap;
            }

            httpBody.put("tasks", tasks);
            imageSyncScanRequest.setHttpContent(
                    org.apache.commons.codec.binary.StringUtils.getBytesUtf8(httpBody.toJSONString()),
                    "UTF-8", FormatType.JSON);
            imageSyncScanRequest.setConnectTimeout(3000);
            imageSyncScanRequest.setReadTimeout(10000);

            // 调用接口
            HttpResponse httpResponse = client.doAction(imageSyncScanRequest);

            // 处理接口响应
            if (httpResponse == null) {
                allErrorMsg.append("审核接口无响应；");
            } else if (!httpResponse.isSuccess()) {
                allErrorMsg.append("审核接口调用失败，状态码：").append(httpResponse.getStatus()).append("；");
            } else {
                // 解析响应内容
                JSONObject scrResponse = JSON.parseObject(
                        org.apache.commons.codec.binary.StringUtils.newStringUtf8(httpResponse.getHttpContent()));
                System.out.println("批量审核接口返回：" + JSON.toJSONString(scrResponse));

                int requestCode = scrResponse.getIntValue("code");
                if (requestCode != 200) {
                    // 整体请求处理失败
                    allErrorMsg.append("批量审核请求处理失败，错误码：").append(requestCode)
                            .append("，信息：").append(scrResponse.getString("msg")).append("；");
                } else {
                    // 解析每张图片的审核结果（taskResults与传入的tasks一一对应）
                    JSONArray taskResults = scrResponse.getJSONArray("data");
                    for (int i = 0; i < taskResults.size(); i++) {
                        JSONObject taskJson = taskResults.getJSONObject(i);
                        int taskCode = taskJson.getIntValue("code");
                        // 单张图片处理失败
                        if (taskCode != 200) {
                            allErrorMsg.append("第").append(i + 1).append("张图片处理失败，错误信息：")
                                    .append(JSON.toJSONString(taskJson)).append("；");
                            continue;
                        }
                        // 解析该图片的场景审核结果
                        JSONArray sceneResults = taskJson.getJSONArray("results");
                        for (Object sceneResult : sceneResults) {
                            JSONObject sceneJson = (JSONObject) sceneResult;
                            String scene = sceneJson.getString("scene");
                            String suggestion = sceneJson.getString("suggestion");
                            String label = sceneJson.getString("label");
                            // 单张图片命中违规
                            if ("block".equals(suggestion)) {
                                allErrorMsg.append("第").append(i + 1).append("张图片，场景【")
                                        .append(scene).append("】命中违规类型：").append(label).append("；");
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            // 接口调用异常（如网络错误、超时等）
            allErrorMsg.append("批量审核调用异常：").append(e.getMessage()).append("；");
            e.printStackTrace();
        }

        // 最终结果判断：无任何错误则整体通过
        if (allErrorMsg.isEmpty()) {
            resultMap.put("success", true);
            resultMap.put("msg", null);
        } else {
            // 移除末尾多余的分号
            String msg = allErrorMsg.toString();
            if (msg.endsWith("；")) {
                msg = msg.substring(0, msg.length() - 1);
            }
            resultMap.put("success", false);
            resultMap.put("msg", msg);
        }

        return resultMap;
    }
}