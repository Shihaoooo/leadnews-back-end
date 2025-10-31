package com.nobody.wemedia.controller;

import com.nobody.model.enums.AppHttpCodeEnum;
import com.nobody.model.wemedia.dtos.WmLoginDto;
import com.nobody.wemedia.service.WmUserService;
import com.nobody.model.dtos.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final WmUserService wmUserService;

    @GetMapping("/test")
    public Result test(){
        return Result.success(AppHttpCodeEnum.SUCCESS);
    }

    @PostMapping("/in")
    public Result login(@RequestBody WmLoginDto dto){
        return wmUserService.login(dto);
    }
}
