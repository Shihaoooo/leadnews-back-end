package com.nobody.wemedia.mapper;

import com.nobody.model.wemedia.pojos.WmUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WmUserMapper {

    @Select("select * from leadnews_wemedia.wm_user where name=#{name} ")
    WmUser getOneByName(String name);

    @Select("select name from leadnews_wemedia.wm_user where ap_user_id = #{userId}")
    String selectById(Integer userId);
}