package com.nobody.wemedia.controller;

import com.nobody.model.dtos.Result;
import com.nobody.wemedia.service.WmChannelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/channels")
@RequiredArgsConstructor
public class WmChannelController {

    final private WmChannelService wmChannelService;

    @Operation(summary = "查询所有频道接口")
    @GetMapping("/list")
    public Result findAll(){
        return wmChannelService.findAll();
    }
}
