package com.nobody.article.sevice;

import com.nobody.model.dtos.ArticleHomeDto;
import com.nobody.model.dtos.Result;

public interface ApArticleService{
    public Result load(ArticleHomeDto dto,Short type);
}
