package com.nobody.article.mapper;

import com.nobody.model.dtos.ArticleHomeDto;
import com.nobody.model.pojos.ApArticle;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApArticleMapper {

    public List<ApArticle> loadArticleList(ArticleHomeDto dto,Short type);
}
