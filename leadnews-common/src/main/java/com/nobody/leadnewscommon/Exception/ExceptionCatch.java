package com.nobody.leadnewscommon.Exception;

import com.nobody.leadnewsmodel.pojo.AppHttpCodeEnum;
import com.nobody.leadnewsmodel.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionCatch {
    // 兜底异常处理器
    @ExceptionHandler(Exception.class)
    public Result exception(Exception e){
        e.printStackTrace();
        log.error("catch exception {}",e.getMessage());

        return Result.error(AppHttpCodeEnum.SERVER_ERROR);
    }

    // 处理可控异常
    @ExceptionHandler(CustomException.class)
    public Result exception(CustomException e){
        log.info("catch exception {}", e.getMessage());

        return Result.error(e.getAppHttpCodeEnum());
    }
}
