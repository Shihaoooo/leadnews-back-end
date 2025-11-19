package com.nobody.test;

import com.nobody.common.aliyun.utils.GreenTextScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class test {

    @Autowired
    private GreenTextScan greenTextScan;
}
