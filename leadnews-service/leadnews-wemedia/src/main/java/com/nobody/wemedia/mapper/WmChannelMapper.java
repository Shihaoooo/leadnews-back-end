package com.nobody.wemedia.mapper;

import com.nobody.model.dtos.Result;
import com.nobody.model.wemedia.pojos.WmChannel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WmChannelMapper {

    @Select("select * from leadnews_wemedia.wm_channel")
    List<WmChannel> findAll();

    // 根据channelId查询channelName
    @Select("select name from leadnews_wemedia.wm_channel where wm_channel.id = #{channelId}")
    String selectById(Integer channelId);
}