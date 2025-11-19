package com.nobody.wemedia.service;


import com.nobody.model.dtos.PageResult;
import com.nobody.model.dtos.Result;
import com.nobody.model.wemedia.dtos.WmNewsDto;
import com.nobody.model.wemedia.dtos.WmNewsPageReqDto;

public interface WmNewsService {

    PageResult findList(WmNewsPageReqDto dto);

    Result submitNews(WmNewsDto dto) throws Exception;
}
