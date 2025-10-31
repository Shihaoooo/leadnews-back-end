package com.nobody.wemedia.service;

import com.nobody.model.dtos.Result;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService {

    public Result uploadPicture(MultipartFile multipartFile) throws Exception;
}
