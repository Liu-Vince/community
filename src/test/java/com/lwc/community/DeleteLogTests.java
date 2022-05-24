package com.lwc.community;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;


/**
 * @author Liu Wenchang
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class DeleteLogTests {
    @Value("${community.path.log}")
    private String logPath;

    @Test
    public void deleteLog(){
        //创建一个文件对象
        File file = new File(logPath);
        // 删除文件
//        deleteDirectory(file);
        try {
            FileUtils.cleanDirectory(file);
        } catch (IOException e) {
//            throw new RuntimeException(e);
        }
//        boolean value = file.delete();
//        System.out.println(value);
    }
}
