package com.nobody.user.controller;

import com.nobody.model.dtos.Login;
import com.nobody.model.dtos.Result;
import com.nobody.model.enums.AppHttpCodeEnum;
import com.nobody.user.sevice.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name="用户接口")
public class UserController {
    // 构造函数注入
    private final UserService userService;

    @GetMapping("/test")
    public Result test(){
        return Result.success("通啦～");
    }

    @PostMapping("/login") // 测试接口
    @Operation(summary = "登陆",description = "根据密码登陆")
    public Result login(@RequestBody Login login){
        // 1 正常登陆
        if (login.getPassword()==null && login.getPhone()==null){
            return Result.error(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
        }
        // 2 根据手机号查询用户
        return  userService.login(login);
    }

    @PostMapping("/Sign") // 注册接口
    @Operation(summary = "注册", description = "根据手机号注册")
    public Result create(@RequestBody Login login){

        return userService.create(login);
    }


}
