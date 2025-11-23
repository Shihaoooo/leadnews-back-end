package com.nobody.model.wemedia.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Schema(description = "文章保存请求Dto")
public class WmNewsDto {
    @Schema(description = "主键")
    private Integer id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "频道id")
    private Integer channelID;

    @Schema(description = "标签")
    private String label;

    @Schema(description = "发布时间")
    private Date publishTime;

    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "文章封面")
    private String cover;

    @Schema(description = "文章封面类型",example = "0:无图;1:单图;3:多图 ; -1:自动")
    private Short type;

    @Schema(description = "状态",example = "提交：1；草稿：0")
    private Short status;

    @Schema(description = "图片集合")
    private List<String> images;

    @Schema(description = "上架或下架")
    private Integer enable;
}
