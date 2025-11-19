package com.nobody.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "分页请求参数")
@Data
public class
PageRequestDto {

    protected Integer size;
    protected Integer currentPage;

    public void checkParam() {
        if (this.size == null || this.size <= 0) {
            size = 10;
        }
        if (this.currentPage == null || this.currentPage <= 0 || this.currentPage > 100) {
            currentPage = 1;
        }
    }
}
