package com.nobody.article.controller;

import com.nobody.article.sevice.ApArticleService;
import com.nobody.common.constants.ArticleConstants;
import com.nobody.model.dtos.ArticleDto;
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
    final private ApArticleService apArticleService;



    // 首页加载文章
    @PostMapping("/load")
    @Operation(summary = "加载" ,description = "加载(首页)")
    public Result load(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    // 下拉加载更多
    @PostMapping("/loadmore")
    @Operation(summary = "加载更多",description = "下拉刷新")
    public Result loadmore(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto,ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    // 上拉加载最新
    @PostMapping("/loadnew")
    @Operation(summary = "加载更多",description = "上拉刷新")
    public Result loadnew(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto,ArticleConstants.LOADTYPE_LOAD_NEW);
    }

    // 修改或保存文章
    @PostMapping("/save")
    @Operation(description = "修改或保存文章")
    public Result saveArticle(@RequestBody ArticleDto dto) throws Exception {
        return apArticleService.saveArticle(dto);
    }




}
