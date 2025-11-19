package com.nobody.wemedia.controller;

import com.nobody.model.dtos.PageResult;
import com.nobody.model.dtos.Result;
import com.nobody.model.enums.AppHttpCodeEnum;
import com.nobody.model.wemedia.dtos.WmMaterialDto;
import com.nobody.wemedia.service.WmMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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


    @Operation(summary = "上传图片接口")
    @PostMapping("/upload_picture")
    public Result uploadPicture(MultipartFile multipartFile) throws Exception {
        return wmMaterialService.uploadPicture(multipartFile);
    }

    @Operation(summary = "查询素材列表接口")
    @PostMapping("/list")
    public PageResult findList(@RequestBody WmMaterialDto wmMaterialDto){
       return wmMaterialService.findList(wmMaterialDto);
    }
}
