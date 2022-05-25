package com.lwc.community.config.quartz;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;

/**
 * @author Liu Wenchang
 */
public class DeleteLogJob implements Job{

    private static final Logger logger = LoggerFactory.getLogger(PostScoreRefreshJob.class);
    @Value("${community.path.log}")
    private String logPath;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //创建一个文件对象
        File file = new File(logPath);
        // 删除文件
//        deleteDirectory(file);
        try {
            FileUtils.cleanDirectory(file);
            logger.info("logs被清理");
        } catch (IOException e) {
            logger.error("logs清理失败"+e.getMessage());
//            throw new RuntimeException(e);
        }
    }
}
