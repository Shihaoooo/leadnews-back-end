package com.nobody.wemedia.service.impl;


import com.nobody.common.aliyun.utils.GreenImageScan;
import com.nobody.common.aliyun.utils.GreenTextScan;
import com.nobody.file.service.impl.MinIOStorageServer;
import com.nobody.leadnewsfeignapi.article.apis.IArticleClient;
import com.nobody.model.dtos.ArticleDto;
import com.nobody.model.dtos.Result;
import com.nobody.model.wemedia.pojos.WmNews;
import com.nobody.wemedia.mapper.WmChannelMapper;
import com.nobody.wemedia.mapper.WmNewsMapper;
import com.nobody.wemedia.mapper.WmUserMapper;
import com.nobody.wemedia.service.WmNewsAutoScanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class WmNewsAutoScanServiceImpl implements WmNewsAutoScanService {

    /** mapper **/
    final private WmNewsMapper wmNewsMapper;

    final private WmChannelMapper wmChannelMapper;

    final private WmUserMapper wmUserMapper;

    /** tools **/
    final private GreenTextScan greenTextScan;

    final private GreenImageScan greenImageScan;

    final private MinIOStorageServer minIOStorageServer;

    final private IArticleClient articleClient;

    @Async // 异步调用
    @Override
    public void autoScanWmNews(Integer id) throws Exception {
        // 1.查询自媒体文章
        WmNews wmNews = wmNewsMapper.selectById(id);

        /** 测试阶段无需审核 **/
//        // 2.审核文本内容 （阿里云接口)
//        if(!textHandler(wmNews.getContent(), wmNews)){
//            // 审核失败
//            return Result.error(AppHttpCodeEnum.PARAM_INVALID.getCode(),"审核未通过");
//        }
//
//        // 3.审核图片
//        if(!imgHandler(wmNews.getImages(),wmNews)){
//            // 审核失败
//            return Result.error(AppHttpCodeEnum.PARAM_INVALID.getCode(),"审核未通过");
//        }

        // 4. 审核成功 保存app端端相关文章
        Result result = saveAppArticle(wmNews);
        if(!result.getCode().equals(200)){
            throw new RuntimeException("WmNewsAutoScanServiceImpl-文章审核，保存app相关文章数据失败！");
        }
        // 回填articleId
        wmNews.setArticleId((long) result.getData());

        // 修改自媒体状态
        wmNews.setStatus(9);

        // 提交修改
        wmNewsMapper.updateById(wmNews);


    }


    private Result saveAppArticle(WmNews wmNews){
        ArticleDto dto = new ArticleDto();
        // 属性拷贝
        BeanUtils.copyProperties(wmNews,dto);
        // 单独设置文章布局
        dto.setLayout(wmNews.getType());
        // 单独设置频道
        String channelName = wmChannelMapper.selectById(wmNews.getChannelId());
        if (channelName != null && !channelName.isEmpty()) {
            dto.setChannelName(channelName);
        }
        // 单独设置usrId
        String authorName = wmUserMapper.selectById(wmNews.getUserId());
        if(authorName != null && !authorName.isEmpty()){
            dto.setAuthorName(authorName);
        }

        if(wmNews.getArticleId() != null){
            // 设置文章id
            long articleId = wmNews.getArticleId();
            dto.setId(articleId);
        }

        return articleClient.saveArticle(dto);
    }

    public boolean textHandler(String content, WmNews wmNews){
        boolean flag = true;

        if ((wmNews.getTitle() + content).isEmpty()){
            return flag;
        }

        try{
            Map<String, Object> scanResult = greenTextScan.textScan(wmNews.getContent());

            if ( !(boolean) scanResult.get("success")) {
                // 2.1 审核不通过
                wmNews.setStatus(2);
                wmNews.setReason("当前文本中存在违规内容");
                wmNewsMapper.updateById(wmNews);
                flag = false;
            }
        }catch (Exception e){
            wmNews.setStatus(2);
            wmNews.setReason("审核出现错误，请稍候重试");
            wmNewsMapper.updateById(wmNews);
            flag = false;
        }

        // 审核通过
        return flag;
    }

    public boolean imgHandler(List<String> imgs, WmNews wmNews) throws Exception {
        boolean flag = true;

        if (imgs.isEmpty()){
            return flag;
        }
        // 1. 图片去重
        imgs = imgs.stream().distinct().collect(Collectors.toList());

        List<byte[]> imgList = new ArrayList<>();

        // 从MinIO中下载图片
        for (String image: imgs) {
            byte[] bytes = minIOStorageServer.downLoadFile(image);
            imgList.add(bytes);
        }

        Map<String,Object> scanResult = greenImageScan.imageScan(imgList);
        // 审核图片
        try{
            if ( !(boolean) scanResult.get("success")) {
                // 2.1 审核不通过
                wmNews.setStatus(2);
                wmNews.setReason("当前图片中存在违规内容");
                wmNewsMapper.updateById(wmNews);
                flag = false;
            }
        }catch (Exception e){
            wmNews.setStatus(2);
            wmNews.setReason("审核出现错误请重试");
            wmNewsMapper.updateById(wmNews);
            flag = false;
        }




        return flag;
    }
}
