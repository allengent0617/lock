package com.gao.bing.fa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author : allengent@163.com
 * @date : 2019/10/22 10:21
 * description :
 */
@Component
@Slf4j
public class TestRunner  implements CommandLineRunner{

    @Autowired
    private RestTemplate restTemplate;

    private Executor ex = Executors.newFixedThreadPool(100);

    @Override
    public void run(String... args) throws Exception {

        CountDownLatch c=new CountDownLatch(1);

        for (int i=0;i<10000;i++)
        {
            log.info("新建任务 i={}",i+1);
            ex.execute(()->{
                try {
                    c.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                doPost2();

            });
        }

        c.countDown();


    }

    public void  doPost1()
    {
        String url = "http://localhost:8080/buy1?id=1&num=1";

        restTemplate.postForEntity(url,null,null);
    }

    public void  doPost2()
    {
        String url = "http://localhost:8080/buy2?id=1&num=1";

        restTemplate.postForEntity(url,null,null);
    }

    public void  doPost3()
    {
        String url = "http://localhost:8080/buy3?id=1&num=1";

        restTemplate.postForEntity(url,null,null);
    }

    /**
     * 测试乐观锁 时 需要添加 Goods 实体  version 字段，见Goods实体  最下面 注释掉的部分
     */
    public void  doPost4()
    {
        String url = "http://localhost:8080/buy4?id=1&num=1";

        restTemplate.postForEntity(url,null,null);
    }

}
