package com.nobody.file.service.impl;

import com.nobody.file.config.MinioProperties;
import com.nobody.file.service.MinIOStorageService;
import io.micrometer.common.util.StringUtils;
import io.minio.*;

import lombok.extern.slf4j.Slf4j;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j

public class MinIOStorageServer implements MinIOStorageService {
    public MinIOStorageServer(MinioProperties minioProperties){
        this.minioProperties = minioProperties;

        this.minioClient = MinioClient.builder()
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .endpoint(minioProperties.getBucket())
                .build();
    }

    private final MinioProperties minioProperties;

    private final MinioClient minioClient;


    /*
     * @Param prefix 文件前缀
     * @Param filename 文件名
     * @Param inputSteam 文件流
     * @return 文件全路径
     *
     * */


    @Override
    // 用于构建在MinIO中的路径
    public String filePathBuilder(String dirPath, String filename) {
         StringBuilder stringBuilder = new StringBuilder(50);
         // 如果前缀不存在，则为根路径(前缀可以用来指定一个文件夹) /
         if(StringUtils.isEmpty(dirPath)){
             stringBuilder.append(dirPath).append("/");
         }

         // 生成模版时间字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/dd");
         String todayStr = sdf.format(new Date());
         stringBuilder.append(todayStr).append("/"); //  dirPath/2025/10/15/
         stringBuilder.append(filename);  // dirPath/2025/10/15/filename
         return stringBuilder.toString();
    }

    @Override
    public String putImage(String prefix, String filename, InputStream inputStream) throws Exception {
        String filePath = filePathBuilder(prefix,filename);
        try{
            // 构建上传参数
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("image/jpg")
                    .bucket(minioProperties.getBucket())
                    .stream(inputStream,-1 ,-1)
                    .build();

            // 上传文件
            minioClient.putObject(putObjectArgs);

            // 构建返回URL
            // "http://10.141.92.90:9000"
            // "http://10.141.92,90:9000/leadnews"
            // "http://10.141.92.90:9000/leadnews/"
            // "http://10.141.92.90:9000/leadnews/dirPath/2025/10/15/filename
            return minioProperties.getServerLocal() + "/" + minioProperties.getBucket() +  // "http://10.141.92,90:9000/leadnews"
                    "/" + // "http://10.141.92.90:9000/leadnews/"
                    filePath;

        }catch (Exception e){
            e.printStackTrace();
            log.error("上传失败");
            throw  new Exception("上传文件失败");
        }
    }

    @Override
    public String putHtml(String prefix, String filename, InputStream inputStream) throws Exception {
        String filePath = filePathBuilder(prefix,filename);
        try{
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("text/html")
                    .bucket(minioProperties.getBucket())
                    .stream(inputStream,-1,-1)
                    .build();

            minioClient.putObject(putObjectArgs);
            return minioProperties.getServerLocal() + "/" + minioProperties.getBucket() +
                    "/" +
                    filePath;
        }catch (Exception e){
            e.printStackTrace();
            log.error("上传失败");
            throw new Exception("上传文件失败");
        }
    }



    /*
    * @Param pathUrl -> 文件全路径
    * */
    @Override
    public void del(String pathUrl) throws Exception {
        String key = pathUrl.replace(minioProperties.getServerLocal()+ "/" , "");
        int index = key.indexOf("/");
        String bucket = key.substring(0,index);
        String filePath = key.substring(index + 1);
        //删除Object
        RemoveObjectArgs roa = RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(filePath)
                .build();
        try {
            minioClient.removeObject(roa);
            log.info("删除成功 {}",filePath);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("删除失败");
        }
    }

    @Override
    public byte[] downLoadFile(String pathUrl) throws Exception {
        String key = pathUrl.replace(minioProperties.getServerLocal()+ "/" , "");
        int index = key.indexOf("/");
        String bucket = key.substring(0,index);
        String filePath = key.substring(index + 1);
        InputStream inputStream;

        try{
            inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucket)
                            .object(filePath)
                            .build()
            );
        }catch (Exception e){
            log.error("下载失败");
            throw new Exception("下载失败");
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while(true){
            try{
                if(!((rc = inputStream.read(buff , 0, 100)) > 0)) break;
            }catch (Exception e){
                e.printStackTrace();
            }
            byteArrayOutputStream.write(buff, 0,rc);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
