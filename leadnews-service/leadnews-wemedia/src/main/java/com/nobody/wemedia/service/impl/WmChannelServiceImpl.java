package com.nobody.wemedia.service.impl;

import com.nobody.model.dtos.Result;
import com.nobody.model.enums.AppHttpCodeEnum;
import com.nobody.model.wemedia.pojos.WmChannel;
import com.nobody.wemedia.mapper.WmChannelMapper;
import com.nobody.wemedia.service.WmChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class WmChannelServiceImpl implements WmChannelService {

    final private WmChannelMapper wmChannelMapper;


    @Override
    public Result findAll() {
        // 1. 查询数据
        List<WmChannel> list = wmChannelMapper.findAll();

        // 2. 返回结果
        if(CollectionUtils.isEmpty(list)){
            return Result.error(AppHttpCodeEnum.EMPTY_FILE);
        }
        else{
            return Result.success(list);
        }
    }
}