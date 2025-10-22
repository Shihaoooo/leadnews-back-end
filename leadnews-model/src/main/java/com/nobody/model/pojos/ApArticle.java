package com.nobody.model.pojos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "文章信息表，存储已发布的文章")
public class ApArticle {

    @Schema(description = "主键ID（自增）", example = "1383828014629179394")
    private Long id;

    @Schema(description = "文章标题", example = "AI技术如何改变未来生活")
    private String title;

    @Schema(description = "文章作者的ID", example = "1001")
    private Integer authorId;

    @Schema(description = "作者昵称", example = "科技达人")
    private String authorName;

    @Schema(description = "文章所属频道ID", example = "1")
    private Integer channelId;

    @Schema(description = "频道名称", example = "科技")
    private String channelName;

    @Schema(description = "文章布局（0：无图文章；1：单图文章；2：多图文章）", example = "1")
    private Integer layout;

    @Schema(description = "文章标记（0：普通文章；1：热点文章；2：置顶文章；3：精品文章；4：大V文章，多标记用逗号分隔）", example = "1,3")
    private String flag;

    @Schema(description = "文章标签（最多3个，逗号分隔）", example = "AI,科技,未来")
    private String labels;

    @Schema(description = "点赞数量（对应数据库字段`like`）", example = "235")
    private Integer likeCount;  // 避免Java关键字冲突，命名为likeCount，对应数据库`like`字段

    @Schema(description = "收藏数量", example = "48")
    private Integer collection;

    @Schema(description = "评论数量", example = "12")
    private Integer comment;

    @Schema(description = "阅读数量", example = "1568")
    private Integer readCount;

    @Schema(description = "所属省市ID", example = "110000")
    private Integer provinceId;

    @Schema(description = "所属市区ID", example = "110100")
    private Integer cityId;

    @Schema(description = "所属区县ID", example = "110101")
    private Integer countyId;

    @Schema(description = "创建时间", example = "2025-01-15T08:30:00")
    private LocalDateTime createdTime;

    @Schema(description = "发布时间", example = "2025-01-15T09:00:00")
    private LocalDateTime publishTime;

    @Schema(description = "同步状态（0：未同步；1：已同步）", example = "1")
    private Integer syncStatus;

    @Schema(description = "来源（0：原创；1：转载）", example = "0")
    private Integer origin;

    @Schema(description = "静态页面URL", example = "https://static.example.com/ai-future.html")
    private String staticUrl;
}