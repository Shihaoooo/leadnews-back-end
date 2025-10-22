package com.nobody.article.controller;

import com.nobody.article.sevice.ApArticleService;
import com.nobody.common.constants.ArticleConstants;
import com.nobody.model.dtos.ArticleHomeDto;
import com.nobody.model.dtos.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {

    final private ApArticleService apArticleService;

    @GetMapping("/test")
    public Result test(){
        return Result.success("通啦～");
    }

    @PostMapping("/load")
    public Result load(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    @PostMapping("/loadmore")
    public Result loadmore(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto,ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    @PostMapping("/loadnew")
    public Result loadnew(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto,ArticleConstants.LOADTYPE_LOAD_NEW);
    }

}
