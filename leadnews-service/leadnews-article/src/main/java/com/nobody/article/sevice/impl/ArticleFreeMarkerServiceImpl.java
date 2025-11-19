package com.nobody.article.sevice.impl;

import com.nobody.article.mapper.ApArticleMapper;
import com.nobody.article.sevice.ApArticleService;
import com.nobody.file.service.impl.MinIOStorageServer;
import com.nobody.model.pojos.ApArticle;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleFreeMarkerServiceImpl {
    /**
     *  生成静态文件上传到MinIO中
     * @param apArticle
     * @param content
     *
     * **/
    private final Configuration configuration;

    private final MinIOStorageServer minIOStorageServer;

    private final ApArticleMapper apArticleMapper;

    @Async
    public void buildApArticleToMinIO(ApArticle apArticle, String content) throws Exception {
        // 1. 参数检查
        if(StringUtils.isNotBlank(content)){
            Template template = null;
            try{
                template = configuration.getTemplate("article.ftl");
            }catch (Exception e){
                e.printStackTrace();
            }

            // 数据模型
            Map<String ,Object> contentDataModel = new HashMap<>();
            contentDataModel.put("content", content);
            StringWriter out = new StringWriter();

            // 合成
            if (template != null) {
                template.process(contentDataModel,out);

                // 上传到MinIO
                InputStream in = new ByteArrayInputStream(out.toString().getBytes());
                String storagePath = minIOStorageServer.putHtml("",content + ".html",in);

                if(StringUtils.isNotBlank(storagePath)){
                    // 非空才赋值
                    apArticle.setStaticUrl(storagePath);
                }
                else{
                    throw new RuntimeException("MinIO服务器出现异常");
                }
            }
        }
    }
}
