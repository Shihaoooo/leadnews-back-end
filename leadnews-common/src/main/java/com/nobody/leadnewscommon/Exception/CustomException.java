package com.nobody.leadnewscommon.Exception;


import com.nobody.leadnewsmodel.pojo.AppHttpCodeEnum;

public class CustomException extends RuntimeException {

    private AppHttpCodeEnum appHttpCodeEnum;

    public CustomException(AppHttpCodeEnum appHttpCodeEnum){
        this.appHttpCodeEnum = appHttpCodeEnum;
    }

    public AppHttpCodeEnum getAppHttpCodeEnum(){
        return appHttpCodeEnum;
    }



}
