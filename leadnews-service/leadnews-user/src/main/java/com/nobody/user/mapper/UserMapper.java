package com.nobody.user.mapper;

import com.nobody.model.pojos.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    // 登陆（根据手机号查询密码）
    @Select("select * from leadnews_user.ap_user where phone = #{phone}")
    public User login(String phone, String password);


}
