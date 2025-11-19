package com.nobody.model.dtos;

import com.nobody.model.enums.AppHttpCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(name = "响应结果类")
public class Result {

    @Schema(description = "返回的状态码")
    private Integer code;   // 代码

    @Schema(description = "信息")
    private String msg;  // 返回信息

    @Schema(description = "数据")
    private Object data;  // 返回数据

    @Schema(description = "请求头信息")
    private String host;  // 请求头信息

    public Result() {
        this.code = 200;
    }

    public Result(Integer code,Object data){
        this.code = code;
        this.data = data;
    }

    public Result(Integer code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer code ,String msg){
        this.code = code;
        this.msg = msg;
    }
    // 无数据成功
    public static Result success(Integer code){
        return new Result(code,AppHttpCodeEnum.SUCCESS.getMessage(),null);

    }
    // 有数据成功
    public static <T> Result success(T data){
        return new Result(1, AppHttpCodeEnum.SUCCESS.getMessage(), data);
    }

    // 自定义成功信息
    public static <T> Result success(Integer code,String msg,T data){
        return new Result(code,msg,data);
    }

    public static Result success(Integer code, String msg) {
        return new Result(code,msg);
    }

    // 自定义错误信息
    public static Result error(Integer code, String msg){
        return new Result(code,msg);
    }

    public static Result error(AppHttpCodeEnum appHttpCodeEnum){
        return new Result(appHttpCodeEnum.getCode(), appHttpCodeEnum.getMessage(),null, null);
    }


}
