package com.nobody.wemedia.service;

import com.nobody.model.wemedia.dtos.WmLoginDto;
import com.nobody.model.wemedia.pojos.WmUser;
import com.nobody.model.dtos.Result;

public interface WmUserService {

    /**
     * 自媒体端登录
     * @param dto
     * @return
     */
    public Result login(WmLoginDto dto);

}