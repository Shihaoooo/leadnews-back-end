package com.nobody.user.sevice.impl;

import com.nobody.model.dtos.Login;
import com.nobody.model.dtos.Result;
import com.nobody.model.enums.AppHttpCodeEnum;
import com.nobody.model.pojos.User;
import com.nobody.user.mapper.UserMapper;
import com.nobody.user.sevice.UserService;
import com.nobody.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // 构造函数注入
    private final UserMapper userMapper;

    @Override
    public Result login(Login login) {
         User user = userMapper.login(login.getPhone(), login.getPassword());

        // 空数据，无用户(不允许游客登陆)
        if (user == null){
            return Result.error(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
        }

        // 比对密码
        String salt = user.getSalt();
        String password = user.getPassword();
        String pwd = user.getPassword();
        if (pwd.equals(login.getPassword())){
            // 返回数据
            JWTUtils jwtUtils = new JWTUtils();
            String token = jwtUtils.generateToken(user.getId().toString());

            Map<String, Object> map = new HashMap<>();
            map.put("token",token);

            // 舍弃敏感信息
            user.setSalt("");
            user.setPassword("");

            map.put("user",user);
            return Result.success(map);
        }
        else{
            return Result.error(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }



    }
}
