package com.nobody.model.pojos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "APP已发布文章内容表")
public class ApArticleContent {

    @Schema(description = "主键ID（自增）", example = "1383828014650150915")
    private Long id;

    @Schema(description = "关联的文章ID（对应ap_article表的id）", example = "1383828014629179394")
    private Long articleId;

    @Schema(description = "文章详细内容（长文本）", example = "本文将从医疗、教育、交通三个领域，详细介绍AI技术如何改变未来生活...")
    private String content;
}
