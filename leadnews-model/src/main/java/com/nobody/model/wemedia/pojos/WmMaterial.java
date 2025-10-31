package com.nobody.model.wemedia.pojos;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "自媒体图文素材信息实体类")
public class WmMaterial {

    @Schema(description = "主键（自增）", example = "1")
    private Long id;

    @Schema(description = "自媒体用户ID", example = "1001")
    private Integer userId;

    @Schema(description = "图片/视频地址", example = "https://cdn.example.com/image/123.jpg")
    private String url;

    @Schema(description = "素材类型（0-图片，1-视频）", example = "0")
    private Integer type;

    @Schema(description = "是否收藏（1-是，0-否）", example = "1")
    private Integer isCollection;

    @Schema(description = "创建时间", example = "2025-11-01T10:30:00")
    private LocalDateTime createdTime;
}
