package com.lwc.community.quartz;

import com.lwc.community.util.CommunityConstant;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;

/**
 * @author Liu Wenchang
 */
public class DeleteLogJob implements Job{
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
