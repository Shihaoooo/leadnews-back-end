package com.nobody.wemedia.mapper;

import com.nobody.model.wemedia.dtos.WmNewsDto;
import com.nobody.model.wemedia.dtos.WmNewsPageReqDto;
import com.nobody.model.wemedia.pojos.WmNews;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


import java.util.List;

@Mapper
public interface WmNewsMapper {

    // 查询所有文章
    List<WmNews> findAll(WmNewsPageReqDto dto);

    // 保存自媒体文章
    @Insert("insert into leadnews_wemedia.wm_news(user_id, title, content, type, channel_id, labels, created_time, submited_time, cover,status, publish_time, reason, article_id, images,enable)" +
            "values (#{userId}, #{title}, #{content}, #{type}, #{channelId}, #{labels}, #{createdTime}, #{submitedTime},#{cover}, #{status}, #{publishTime}, #{reason}, #{articleId}, #{images},#{enable})")
    void save (WmNews wmNews);

    // 根据id查询自媒体文章
    @Select("select * from leadnews_wemedia.wm_news where user_id = #{id}")
    WmNews selectById(Integer id);

    // 万能更新方法
    void updateById(WmNews wmNews);
}
