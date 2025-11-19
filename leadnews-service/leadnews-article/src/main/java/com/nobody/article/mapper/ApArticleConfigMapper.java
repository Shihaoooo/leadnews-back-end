package com.nobody.article.mapper;

import com.nobody.model.pojos.ApArticleConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApArticleConfigMapper {

    @Insert("insert into leadnews_article.ap_article_config(article_id, is_comment, is_forward, is_down, is_delete) VALUES (#{articleId},#{idComment},#{isForward},#{isDown},#{idDelete})")
    void save(ApArticleConfig apArticleConfig);
}
