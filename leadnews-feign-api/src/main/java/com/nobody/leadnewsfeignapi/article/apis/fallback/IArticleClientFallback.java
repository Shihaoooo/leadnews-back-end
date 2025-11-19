package com.nobody.leadnewsfeignapi.article.apis.fallback;


import com.nobody.leadnewsfeignapi.article.apis.IArticleClient;
import com.nobody.model.dtos.ArticleDto;
import com.nobody.model.dtos.Result;
import com.nobody.model.enums.AppHttpCodeEnum;
import org.springframework.stereotype.Component;

@Component
public class IArticleClientFallback implements IArticleClient {
    @Override
    public Result saveArticle(ArticleDto dto) {
        return Result.error(AppHttpCodeEnum.SERVER_ERROR.getCode(),"数据获取失败");
    }
}
