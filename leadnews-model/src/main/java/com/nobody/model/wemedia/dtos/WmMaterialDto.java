package com.nobody.model.wemedia.dtos;

import com.nobody.model.dtos.PageRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "自媒体分页素材查询Dto")
public class WmMaterialDto extends PageRequestDto {

    /*
    * 1.查询收藏素材
    * 0.未收藏（查询全部素材）*/
    private Short isCollection = 0; // 默认查询全部素材

}
