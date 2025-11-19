package com.nobody.model.wemedia.pojos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "自媒体图文素材关系表")
public class WmNewsMaterial {

    @Schema(description = "主键", example = "1")
    private Integer id;

    @Schema(description = "图文内容ID", example = "1001")
    private Integer newsId;

    @Schema(description = "素材ID", example = "2001")
    private Integer materialId;

    @Schema(description = "引用类型（0：图片；1：视频）", example = "0" , allowableValues = {"0", "1"})
    private Integer type;

    @Schema(description = "引用排序")
    private Integer ord;
}
