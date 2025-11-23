package com.nobody.article.sevice;

import com.nobody.model.pojos.ApArticle;
import org.springframework.scheduling.annotation.Async;

public interface ArticleFreeMarkerService {

    @Async
    void buildApArticleToMinIO(ApArticle apArticle, String content) throws Exception;
}
