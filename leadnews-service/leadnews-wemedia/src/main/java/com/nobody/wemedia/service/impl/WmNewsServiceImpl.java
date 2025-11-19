package com.nobody.wemedia.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.nobody.model.dtos.PageResult;
import com.nobody.model.dtos.Result;
import com.nobody.model.enums.AppHttpCodeEnum;
import com.nobody.model.wemedia.dtos.WmNewsDto;
import com.nobody.model.wemedia.dtos.WmNewsPageReqDto;
import com.nobody.model.wemedia.pojos.WmNews;
import com.nobody.utils.ThreadLocalUtils;
import com.nobody.wemedia.mapper.WmNewsMapper;
import com.nobody.wemedia.service.WmNewsAutoScanService;
import com.nobody.wemedia.service.WmNewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class WmNewsServiceImpl implements WmNewsService {

    final private WmNewsMapper wmNewsMapper;

    final private WmNewsAutoScanService wmNewsAutoScanService;
    // 查询所有文章
    @Override
    public PageResult findList(WmNewsPageReqDto dto) {
        // 1. 检查参数
        dto.checkParam();
        // 2. 分页查询

        // 2.1 开始分页
        PageHelper.startPage(dto.getCurrentPage(),dto.getSize());

        log.info("UserId:{}",ThreadLocalUtils.get());

        // 2.2 执行查询(根据状态,频道,时间范围,关键字,当前登录人,等条件查询)
        // 2.2.1 传递当前用户ID
        dto.setUserID(Integer.valueOf(ThreadLocalUtils.get().toString()));

        // 2.2.2 执行查询
        Page<WmNews> page =(Page<WmNews>) wmNewsMapper.findAll(dto);

        // 3.返回结果
        return new PageResult(page.getTotal(),page.getResult(),dto.getSize(),dto.getCurrentPage());
    }

    // 提交（批量提交）
    @Override
    public Result submitNews(WmNewsDto dto) throws Exception {
        // 条件判断
        if (dto == null || dto.getId() == null ){
            return Result.error(AppHttpCodeEnum.EMPTY_FILE);
        }
        // 1. 保存或修改文章
        // 1.1 新建文章对象
        WmNews wmNews = new WmNews();

        // 1.2 属性拷贝( dto -> wmNews ),只拷贝类型和属性名相同的
        BeanUtils.copyProperties(dto,wmNews);

        // 1.3 单独处理图片
        List<String> images = dto.getImages();

        wmNews.setImages(images);

        // 1.4 如果封面为自动(cover = null)
        if(dto.getCover() == null){
            if( images != null && !images.isEmpty()){
                // 自动计算封面类型随机选取图片中的一张
                wmNews.setCover(images.get(new Random().nextInt(0,images.size())));
            }
        }

        // 保存/修改文章
        saveOrUpdateWmNews(wmNews);

        // 审核文章
        wmNewsAutoScanService.autoScanWmNews(wmNews.getId());

        return Result.success(AppHttpCodeEnum.SUCCESS);
    }

    private void saveOrUpdateWmNews(WmNews wmNews) {
        // 补全属性
        wmNews.setUserId(ThreadLocalUtils.get());
        wmNews.setCreatedTime(LocalDateTime.now());
        wmNews.setSubmitedTime(LocalDateTime.now());
        wmNews.setEnable(1);  // 默认上架

        if (wmNews.getId() == null) {
            // 保存
            wmNewsMapper.save(wmNews);
        }
        else {
            // 修改
            wmNewsMapper.updateById(wmNews);

        }
    }
}
