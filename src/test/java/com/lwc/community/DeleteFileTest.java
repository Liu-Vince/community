package com.lwc.community;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Date;

import static java.lang.Thread.sleep;

/**
 * @author Liu Wenchang
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class DeleteFileTest {
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    String path = "D://project/data/wk-images/1.txt";

    @Test
    public void deleteShare() throws InterruptedException {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
//                    sleep(10000);
                    File file = new File(path);
                    FileUtils.forceDelete(file);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Date startTime = new Date(System.currentTimeMillis() + 10000);
        taskScheduler.scheduleAtFixedRate(task, startTime, 1000);

//        sleep(30000);
    }
}
