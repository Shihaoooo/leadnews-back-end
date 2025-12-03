package com.nobody.article.controller;

import com.alibaba.fastjson2.JSON;
import com.nobody.article.mapper.ApArticleConfigMapper;
import com.nobody.article.mapper.ApArticleMapper;
import com.nobody.model.pojos.ApArticle;
import com.nobody.model.pojos.ApArticleConfig;
import com.nobody.model.wemedia.pojos.WmNews;
import com.nobody.utils.ThreadLocalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class ArticleIsDownListener {

    final private ApArticleMapper apArticleMapper;

    final private ApArticleConfigMapper apArticleConfigMapper;


    // 监听文章上下架的消息，修改文章配置
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("article.q1"),
            exchange = @Exchange("article_exchange"),
            key = {"article.upordown"}
    ))
    public void onMessage(String message) throws Exception {
        if(StringUtils.isNotBlank(message)){
            Map map = JSON.parseObject(message,Map.class);
            ApArticle apArticle = new ApArticle();
            ApArticleConfig apArticleConfig = new ApArticleConfig();

            try{
                apArticle.setId((Long) map.get("articleId"));

                if(map.get("enable").equals(1)) apArticleConfig.setIsDown(0);
                else apArticleConfig.setIsDown(1);

            }catch (Exception e){
                throw new Exception("参数类型错误");
            }

            apArticleMapper.updateById(apArticle);
            apArticleConfigMapper.update(apArticleConfig);

        }

    }
}
