package com.nobody.article.controller;

import com.nobody.article.sevice.ApArticleService;
import com.nobody.common.constants.ArticleConstants;
import com.nobody.file.service.impl.MinIOStorageServer;
import com.nobody.model.dtos.ArticleHomeDto;
import com.nobody.model.dtos.Result;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    final private MinIOStorageServer minIOStorageServer;

    final private ApArticleService apArticleService;



    @PostMapping("/load")
    @Operation(summary = "加载" ,description = "加载(首页)")
    public Result load(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    @PostMapping("/loadmore")
    @Operation(summary = "加载更多",description = "下拉刷新")
    public Result loadmore(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto,ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    @PostMapping("/loadnew")
    @Operation(summary = "加载更多",description = "上拉刷新")
    public Result loadnew(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto,ArticleConstants.LOADTYPE_LOAD_NEW);
    }


}
