package com.nobody.article.sevice.impl;


import com.nobody.article.mapper.ApArticleMapper;
import com.nobody.article.sevice.ApArticleService;
import com.nobody.common.constants.ArticleConstants;
import com.nobody.model.dtos.ArticleHomeDto;
import com.nobody.model.dtos.Result;
import com.nobody.model.pojos.ApArticle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApArticleServiceImpl implements ApArticleService{

    final private ApArticleMapper apArticleMapper;

    @Override
    public Result load(ArticleHomeDto dto, Short type) {

        // 1.分页条数校验
        Integer size = dto.getSize();
        if (size == null || size == 0){
            size = 10;
        }
        size = size > ArticleConstants.MAX_PAGE_SIZE ? ArticleConstants.MAX_PAGE_SIZE:size;

        // 2.校验type参数
        if (!type.equals(ArticleConstants.LOADTYPE_LOAD_MORE) &&
        !type.equals(ArticleConstants.LOADTYPE_LOAD_NEW)){
            type = ArticleConstants.LOADTYPE_LOAD_MORE; //默认LOADMODE
        }

        // 3.频道参数校验
        if(dto.getTag() == null){
            dto.setTag(ArticleConstants.DEFAULT_TAG);
        }

        // 4. 时间校验
        if (dto.getMaxBehotTime() == null) dto.setMaxBehotTime(new Date());
        if (dto.getMinBehotTime() == null) dto.setMinBehotTime(new Date());

        // 5. 查询
        List<ApArticle> apArticles = apArticleMapper.loadArticleList(dto, type);

        // 6. 返回
        return Result.success(apArticles);
    }
}
