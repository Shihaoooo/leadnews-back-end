package com.nobody.model.pojos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Schema(description = "APP发布文章配置表")
public class ApArticleConfig {

    @Schema(description = "主键ID（自增）", example = "1383828014645956610")
    private Long id;

    @Schema(description = "关联的文章ID（对应ap_article表的id）", example = "1383828014629179394")
    private Long articleId;

    @Schema(description = "是否可评论（0：不可评论；1：可评论）", example = "1")
    private Integer isComment = 1;

    @Schema(description = "是否可转发（0：不可转发；1：可转发）", example = "1")
    private Integer isForward = 1;

    @Schema(description = "是否下架（0：未下架；1：已下架）", example = "0")
    private Integer isDown = 0;

    @Schema(description = "是否已删除（0：未删除；1：已删除）", example = "0")
    private Integer isDelete = 0;
}
