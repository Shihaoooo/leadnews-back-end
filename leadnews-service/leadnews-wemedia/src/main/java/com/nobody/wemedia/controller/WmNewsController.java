package com.nobody.wemedia.controller;

import com.nobody.model.dtos.PageResult;
import com.nobody.model.dtos.Result;
import com.nobody.model.wemedia.dtos.WmNewsDto;
import com.nobody.model.wemedia.dtos.WmNewsPageReqDto;
import com.nobody.wemedia.service.WmNewsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/news")
public class WmNewsController {

    final private WmNewsService wmNewsService;

    @PostMapping("/list")
    @Operation(summary = "分页查询文章列表接口")
    public PageResult findList(@RequestBody WmNewsPageReqDto dto){
        return wmNewsService.findList(dto);
    }

    @PostMapping("/submit")
    public Result submitNews(@RequestBody WmNewsDto dto) throws Exception {
        return wmNewsService.submitNews(dto);
    }

}
