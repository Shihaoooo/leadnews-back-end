package com.nobody.wemedia.mapper;

import com.nobody.model.wemedia.pojos.WmMaterial;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WmMaterialMapper {

    @Insert("insert into leadnews_wemedia.wm_material(user_id, url, type, is_collection, created_time) values (#{userId}, #{url}, #{type}, #{isCollection}, #{createdTime})")
    void save(WmMaterial wmMaterial);
}
