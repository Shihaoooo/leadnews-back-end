package com.nobody.model.wemedia.dtos;

import com.nobody.model.dtos.PageRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "自媒体文章分页请求参数")
public class WmNewsPageReqDto extends PageRequestDto {

    @Schema(description = "文章状态（0：草稿；1：提交（待审核）；2：审核失败；3：人工审核；4：人工审核通过；8：审核通过（待发布）；9：已发布）", example = "1")
    private Short status;

    @Schema(description = "发布时间开始时间", example = "2023-01-01")
    private Date beginPubDate;

    @Schema(description = "发布时间结束时间", example = "2023-01-31")
    private Date endPubDate;

    @Schema(description = "图文频道ID", example = "5")
    private Integer channelID;

    @Schema(description = "关键字（标题关键字）", example = "SpringBoot")
    private String keyWord;

    @Schema(description = "自媒体用户ID", example = "1001")
    private Integer userID;
}
