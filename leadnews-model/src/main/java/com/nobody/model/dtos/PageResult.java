package com.nobody.model.dtos;

import com.nobody.model.wemedia.pojos.WmMaterial;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Data
@AllArgsConstructor
@Slf4j
@Schema(description = "分页结果封装类")
public class PageResult {
    private Long total; // 总记录数
    private Object list; // 当前页数据列表
    private Integer Size; // 每页记录数
    private Integer currentPage; // 当前页码

}
