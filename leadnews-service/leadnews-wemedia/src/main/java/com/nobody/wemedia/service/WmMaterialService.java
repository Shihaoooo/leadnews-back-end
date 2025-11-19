package com.nobody.wemedia.service;

import com.nobody.model.dtos.PageResult;
import com.nobody.model.dtos.Result;
import com.nobody.model.wemedia.dtos.WmMaterialDto;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService {

    public Result uploadPicture(MultipartFile multipartFile) throws Exception;

    public PageResult findList(WmMaterialDto wmMaterialDto);
}
