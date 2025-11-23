package com.nobody.article.sevice.impl;


import com.nobody.article.mapper.ApArticleConfigMapper;
import com.nobody.article.mapper.ApArticleContentMapper;
import com.nobody.article.mapper.ApArticleMapper;
import com.nobody.article.sevice.ApArticleService;
import com.nobody.article.sevice.ArticleFreeMarkerService;
import com.nobody.common.constants.ArticleConstants;
import com.nobody.model.dtos.ArticleDto;
import com.nobody.model.dtos.ArticleHomeDto;
import com.nobody.model.dtos.Result;
import com.nobody.model.enums.AppHttpCodeEnum;
import com.nobody.model.pojos.ApArticle;
import com.nobody.model.pojos.ApArticleConfig;
import com.nobody.model.pojos.ApArticleContent;
import com.nobody.utils.snowflake.SnowflakeUtils;
import com.nobody.utils.ThreadLocalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApArticleServiceImpl implements ApArticleService{

    final private ApArticleMapper apArticleMapper;

    final private ApArticleConfigMapper apArticleConfigMapper;

    final private ApArticleContentMapper apArticleContentMapper;

    final private SnowflakeUtils snowflakeUtils;

    final private ArticleFreeMarkerService articleFreeMarkerService;

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

    // 保存或者修改文章
    @Override
    public Result saveArticle(ArticleDto dto) throws Exception {
        // 1. 检查参数
        if(dto == null){
            // 1.1 参数为空
            return Result.error(AppHttpCodeEnum.EMPTY_FILE);
        }

        ApArticle apArticle = new ApArticle();

        // 1.2 属性拷贝
        BeanUtils.copyProperties(dto,apArticle);

        // 2.判断是否存在Id
        if(ThreadLocalUtils.get() == null || ThreadLocalUtils.get().toString().isEmpty()){
            // 2.1 不存在id，生成id，并保存
            apArticle.setId(snowflakeUtils.generateId());

            apArticleMapper.save(apArticle);

            // 2.1.1 保存配置
            ApArticleConfig apArticleConfig = new ApArticleConfig();
            apArticleConfig.setArticleId(apArticle.getId());
            apArticleConfigMapper.save(apArticleConfig);

            // 2.2 保存文章内容
            ApArticleContent apArticleContent = new ApArticleContent();
            apArticleContent.setArticleId(apArticle.getId());
            apArticleContentMapper.save(apArticleContent);
        }
        // 2.2 id存在

        // 2.3 生成静态文件（异步调用）
        articleFreeMarkerService.buildApArticleToMinIO(apArticle,dto.getContent());

        // 修改文章

        if (apArticle.getStaticUrl() != null && !apArticle.getStaticUrl().isEmpty()) {
            apArticleMapper.updateById(apArticle);
        }
        else{
            return Result.error(AppHttpCodeEnum.SERVER_ERROR.getCode(),"MinIO出现错误");
        }

        // 3. 结果返回
        return Result.success(AppHttpCodeEnum.SUCCESS);
    }
}
