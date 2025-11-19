package com.nobody.leadnewsfeignapi.article.apis;

import com.nobody.leadnewsfeignapi.article.apis.fallback.IArticleClientFallback;
import com.nobody.model.dtos.ArticleDto;
import com.nobody.model.dtos.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "leadnews-article",fallbackFactory = IArticleClientFallback.class)
public interface IArticleClient {

    @PostMapping("/article/save")
    Result saveArticle(@RequestBody ArticleDto dto);

}
