package com.nobody.article.mapper;

import com.nobody.model.dtos.ArticleHomeDto;
import com.nobody.model.pojos.ApArticle;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApArticleMapper {

    // 加载
    List<ApArticle> loadArticleList(ArticleHomeDto dto,Short type);

    // 保存
    void save(ApArticle apArticle);

    // 修改文章
    void updateById(ApArticle apArticle);
}
