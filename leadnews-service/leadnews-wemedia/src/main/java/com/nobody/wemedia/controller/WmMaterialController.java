package com.nobody.wemedia.controller;

import com.nobody.model.dtos.Result;
import com.nobody.model.enums.AppHttpCodeEnum;
import com.nobody.wemedia.service.WmMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
public class WmMaterialController {

    private final WmMaterialService wmMaterialService;



    @GetMapping("/test")
    public Result test(){
        return Result.success(AppHttpCodeEnum.SUCCESS);
    }

    public Result uploadPicture(MultipartFile multipartFile) throws Exception {

        return wmMaterialService.uploadPicture(multipartFile);
    }
}
