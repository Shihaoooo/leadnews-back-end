package com.nobody.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Schema(name="频道首页查询参数")
@Data
public class ArticleHomeDto {

    @Schema(description = "最大时间")
    private Date maxBehotTime;

    @Schema(description = "最小时间")
    private Date minBehotTime;

    @Schema(description = "频道标签")
    private Object tag;

    @Schema(description = "查询条数")
    private Integer size;

}
