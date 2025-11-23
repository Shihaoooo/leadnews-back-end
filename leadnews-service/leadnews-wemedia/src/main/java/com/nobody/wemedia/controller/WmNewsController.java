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
    @Operation(summary = "自媒体文章提交接口")
    public Result submitNews(@RequestBody WmNewsDto dto) throws Exception {
        return wmNewsService.submitNews(dto);
    }

    @PostMapping("/down_or_up")
    public Result downOrUp(@RequestBody WmNewsDto dto){
        return wmNewsService.downOrUp(dto);
    }

}
