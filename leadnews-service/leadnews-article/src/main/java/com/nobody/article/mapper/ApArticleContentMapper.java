package com.nobody.article.mapper;

import com.nobody.model.pojos.ApArticleContent;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApArticleContentMapper {

    // 保存文章内容
    @Insert("insert into leadnews_article.ap_article_content(article_id, content) VALUES (#{articleId},#{content})")
    void save(ApArticleContent apArticleContent);
}
