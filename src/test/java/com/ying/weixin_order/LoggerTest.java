package com.ying.weixin_order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {

    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test(){
        String name = "yinger";
        String pwd = "19130211";
        logger.info("info.......");
        logger.error("error.......");
        logger.info("name:{},pwd:{}",name,pwd);

    }

    @Test
    public void test1(){
        try {
            Class class1 = Class.forName("com.ying.weixin_order.LoggerTest");
            System.out.println(class1.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
