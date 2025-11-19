package com.nobody.model.wemedia.pojos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "自媒体频道信息表")
@Data
public class WmChannel {
    @Schema(description = "主键ID", example = "1")
    private Integer id;

    @Schema(description = "频道名称", example = "娱乐")
    private String name;

    @Schema(description = "频道描述", example = "娱乐频道，包含明星八卦、影视资讯等内容")
    private String description;

    @Schema(description = "是否默认频道（0：非默认；1：默认）", example = "1")
    private Integer is_default;

    @Schema(description = "频道状态（0：禁用；1：启用）", example = "1")
    private Integer status;

    @Schema(description = "排序")
    private Integer ord;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;
}
