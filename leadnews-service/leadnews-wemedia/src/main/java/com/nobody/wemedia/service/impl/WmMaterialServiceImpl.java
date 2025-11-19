package com.nobody.wemedia.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.nobody.file.service.impl.MinIOStorageServer;
import com.nobody.model.dtos.PageResult;
import com.nobody.model.dtos.Result;
import com.nobody.model.enums.AppHttpCodeEnum;
import com.nobody.model.wemedia.dtos.WmMaterialDto;
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

        wmMaterial.setUserId(Integer.valueOf(ThreadLocalUtils.get().toString()));

        wmMaterial.setUrl(fileId);

        wmMaterial.setType(0); // 0表示图片

        wmMaterial.setCreatedTime(LocalDateTime.now());

        wmMaterial.setIsCollection(0); // 0表示未收藏

        wmMaterialMapper.save(wmMaterial);

        return Result.success(AppHttpCodeEnum.SUCCESS);
    }

    @Override
    public PageResult findList(WmMaterialDto wmMaterialDto) {
        // 1. 检查参数
        wmMaterialDto.checkParam();
        // 2.分页查询

        // 2.1 开启分页
        PageHelper.startPage(wmMaterialDto.getCurrentPage(), wmMaterialDto.getSize());

        // 2.2 执行查询(是否查询收藏素材，用户id)
        if(wmMaterialDto.getIsCollection() == 1){
            // 查询收藏素材
            Page<WmMaterial> page = (Page<WmMaterial>) wmMaterialMapper.findCollectionList(ThreadLocalUtils.get().toString());

            return new PageResult(page.getTotal(),page.getResult(), wmMaterialDto.getCurrentPage(),wmMaterialDto.getSize());
        }
        else{
            // 查询全部素材
            Page<WmMaterial> page = (Page<WmMaterial>) wmMaterialMapper.findList(ThreadLocalUtils.get().toString());

            return new PageResult(page.getTotal(),page.getResult(),wmMaterialDto.getCurrentPage(),wmMaterialDto.getSize());
        }
    }
}
