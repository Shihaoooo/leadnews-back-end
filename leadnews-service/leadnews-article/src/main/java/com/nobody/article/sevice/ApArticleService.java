package com.nobody.article.sevice;

import com.nobody.model.dtos.ArticleDto;
import com.nobody.model.dtos.ArticleHomeDto;
import com.nobody.model.dtos.Result;
import com.nobody.model.pojos.ApArticle;

public interface ApArticleService{

    // 文章加载
    public Result load(ArticleHomeDto dto,Short type);

    // 保存或修改App端文章
    public Result saveArticle(ArticleDto dto) throws Exception;
}
