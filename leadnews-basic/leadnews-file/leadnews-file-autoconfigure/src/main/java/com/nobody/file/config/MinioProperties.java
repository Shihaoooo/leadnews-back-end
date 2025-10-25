package com.nobody.file.config;


import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Getter
@Component
public class MinioProperties implements Serializable {

    final private String ACCESSKEY = "minioadmin";
    final private String SECRETKEY = "minioadmin";

    final private String BUCKET = "leadnews";

    final private String SERVER_LOCAL = "http://10.141.92.90:9000";


}
