package com.lwc.community.service;

import com.lwc.community.controller.ShareController;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;

import static java.lang.Thread.sleep;

/**
 * @author Liu Wenchang
 */
@Service
public class DeleteShareService {

    private static final Logger logger = LoggerFactory.getLogger(ShareController.class);

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    public void deleteShare(String path) {


        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
//                    sleep(10000);
                    File file = new File(path);
                    if (file.exists()) {
                        file.delete();
//                        FileUtils.forceDelete(file);
                    } else {
                        return;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }

        };
        // 60分钟后销毁
        Date startTime = new Date(System.currentTimeMillis() + 1000 *60 * 60);
//        Date startTime = new Date(System.currentTimeMillis() + 1000 );
        taskScheduler.scheduleAtFixedRate(task, startTime,1000);
    }
}
