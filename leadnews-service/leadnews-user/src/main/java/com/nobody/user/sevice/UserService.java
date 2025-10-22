package com.nobody.user.sevice;

import com.nobody.model.dtos.Login;
import com.nobody.model.dtos.Result;

public interface UserService {

    // 登陆
    public Result login(Login login);

    // 注册
    Result create(Login login);
}
