package com.nobody.wemedia.mapper;

import com.nobody.model.wemedia.pojos.WmMaterial;
import com.nobody.model.wemedia.pojos.WmNews;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WmMaterialMapper {

    @Insert("insert into leadnews_wemedia.wm_material(user_id, url, type, is_collection, created_time) values (#{userId}, #{url}, #{type}, #{isCollection}, #{createdTime})")
    void save(WmMaterial wmMaterial);

    @Select("select * from leadnews_wemedia.wm_material where is_collection = 1 and user_id = #{userID} order by created_time desc")
    List<WmMaterial> findCollectionList(String userID);

    @Select("select * from leadnews_wemedia.wm_material where user_id = #{userID} order by created_time desc")
    List<WmMaterial> findList(String userID);
}
