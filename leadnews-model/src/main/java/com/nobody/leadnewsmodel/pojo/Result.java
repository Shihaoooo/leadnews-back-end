package com.nobody.leadnewsmodel.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Result {

    private Integer code;
    private String msg;
    private Object data;

    // 无参成功
    public static Result success(){
        return new Result(1, "success", null);
    }

    public static Result success(Object data){
        return new Result(1, "success", data);
    }

    public static Result error(String msg){
        return new Result(0,msg,null);
    }

    public static Result error(AppHttpCodeEnum appHttpCodeEnum){
        return new Result(appHttpCodeEnum.getCode(), appHttpCodeEnum.getErrorMessage(),null);
    }
}
