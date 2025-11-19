package com.nobody.model.wemedia.pojos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "自媒体图文内容信息表")
@Data
public class WmNews {

    @Schema(description = "主键", example = "1")
    private Integer id;

    @Schema(description = "自媒体用户ID", example = "1001")
    private Integer userId;

    @Schema(description = "标题", example = "SpringBoot实战教程")
    private String title;

    @Schema(description = "图文内容", example = "本文介绍SpringBoot的核心特性...")
    private String content;

    @Schema(
            description = "文章布局（0：无图文章；1：单图文章；3：多图文章）",
            example = "1",
            allowableValues = {"0", "1", "3"}
    )
    private Integer type;

    @Schema(description = "图文频道ID", example = "5")
    private Integer channelId;

    @Schema(description = "标签（多个标签用逗号分隔）", example = "技术,Java")
    private String labels;

    @Schema(description = "创建时间", example = "2025-11-02T10:00:00")
    private LocalDateTime createdTime;

    @Schema(description = "提交时间", example = "2025-11-02T14:30:00")
    private LocalDateTime submitedTime;

    @Schema(description = "封面")
    private String cover;

    @Schema(
            description = "当前状态（0：草稿；1：提交（待审核）；2：审核失败；3：人工审核；4：人工审核通过；8：审核通过（待发布）；9：已发布）",
            example = "1",
            allowableValues = {"0", "1", "2", "3", "4", "8", "9"}
    )
    private Integer status;

    @Schema(description = "定时发布时间（不定时则为空）", example = "2025-11-03T08:00:00")
    private LocalDateTime publishTime;

    @Schema(description = "拒绝理由", example = "内容包含敏感信息")
    private String reason;

    @Schema(description = "发布库文章ID", example = "1000001")
    private Long articleId;

    @Schema(description = "图片（多个图片用逗号分隔）", example = "https://example.com/img1.jpg,https://example.com/img2.jpg")
    private List<String> images;

    @Schema(description = "是否有效（1：有效）", example = "1", defaultValue = "1")
    private Integer enable;


    public enum Status {
        NORMAL((short) 0,"草稿"),
        SUBMIT((short) 1,"未审核（已提交）"),
        FAIL((short) 2,"审核未通过"),
        ADMIN_AUTH((short) 3,"人工审核"),
        ADMIN_SUCCESS((short) 4,"人工审核通过"),
        SUCCESS((short) 8,"审核通过（待发布）"),
        PUBLISHED((short) 9,"已发布");

        Short code;

        Status(Short code, String msg){
            this.code = code;
        }
        public Short getCode(){
            return this.code;
        }
    }

}

