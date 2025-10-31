package com.nobody.wemedia.service.impl;

import com.nobody.file.service.impl.MinIOStorageServer;
import com.nobody.model.dtos.Result;
import com.nobody.model.enums.AppHttpCodeEnum;
import com.nobody.model.wemedia.pojos.WmMaterial;
import com.nobody.utils.ThreadLocalUtils;
import com.nobody.wemedia.mapper.WmMaterialMapper;
import com.nobody.wemedia.service.WmMaterialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WmMaterialServiceImpl implements WmMaterialService {

    // MinIO存储服务
    final private MinIOStorageServer minIOStorageServer;

    // ThreadLocal操作工具类
    final private ThreadLocalUtils<Object> threadLocalUtils;

    final private WmMaterialMapper wmMaterialMapper;
    @Override
    public Result uploadPicture(MultipartFile multipartFile) throws Exception {
        // 1. 检查参数
        if (multipartFile == null || multipartFile.isEmpty()) {
            return Result.error(AppHttpCodeEnum.EMPTY_FILE);
        }

        // 2. 上传图片到文件服务器（MINIO）
        String fileName = UUID.randomUUID().toString().replace("-", "");

        String originalFilename = multipartFile.getOriginalFilename();
        String postfix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileId;
        try{
            fileId = minIOStorageServer.putImage("",fileName + postfix, multipartFile.getInputStream());
            log.info("上传文件成功，文件路径{}" ,fileId);
        }catch (Exception e){
            e.printStackTrace();
            log.error("上传文件失败");
            throw new Exception("文件上传失败");
        }

        // 3. 将图片路径上传到数据库中
         WmMaterial wmMaterial = new WmMaterial();

        wmMaterial.setUserId(Integer.valueOf(threadLocalUtils.get().toString()));

        wmMaterial.setUrl(fileId);

        wmMaterial.setType(0); // 0表示图片

        wmMaterial.setCreatedTime(LocalDateTime.now());

        wmMaterial.setIsCollection(0); // 0表示未收藏

        wmMaterialMapper.save(wmMaterial);

        return Result.success(AppHttpCodeEnum.SUCCESS);
    }
}
