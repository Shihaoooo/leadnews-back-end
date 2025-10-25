package com.nobody.file.service;

import io.minio.errors.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface MinIOStorageService {


    // 路径构建
    public String filePathBuilder(String dirPath , String filename);

    // 上传图片
    public String putImage(String prefix, String filename , InputStream inputStream) throws Exception;

    // 上传静态页面
    public String putHtml(String prefix , String filename , InputStream inputStream) throws Exception;

    // 删除文件
    public void del(String pathUrl) throws Exception;

    // 下载文件
    public byte[] downLoadFile(String pathUrl) throws Exception;
}
