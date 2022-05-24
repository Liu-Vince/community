package com.lwc.community.config;

import com.lwc.community.quartz.AlphaJob;
import com.lwc.community.quartz.DeleteLogJob;
import com.lwc.community.quartz.PostScoreRefreshJob;
import com.lwc.community.quartz.WKImageDeleteJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * @author Liu Wenchang
 */
@Configuration
public class QuartzConfig {


    // 配置JobDetail
    //@Bean
    public JobDetailFactoryBean alphaJobDetail() {

        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(AlphaJob.class);
        factoryBean.setName("alphaJob");
        factoryBean.setGroup("alphaJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);

        return factoryBean;
    }

    // 配置Trigger(SimpleTriggerFactoryBean, CronTriggerFactoryBean)
    //@Bean
    public SimpleTriggerFactoryBean alphaTrigger(JobDetail alphaJobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(alphaJobDetail);
        factoryBean.setName("alphaTrigger");
        factoryBean.setGroup("alphaTriggerGroup");
        factoryBean.setRepeatInterval(3000);
        factoryBean.setJobDataMap(new JobDataMap());


        return factoryBean;
    }



    // 刷新帖子分数
    @Bean
    public JobDetailFactoryBean postScoreRefreshJobDetail() {

        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(PostScoreRefreshJob.class);
        factoryBean.setName("PostScoreRefreshJob");
        factoryBean.setGroup("communityJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);

        return factoryBean;
    }



    @Bean
    public SimpleTriggerFactoryBean postScoreRefreshTrigger(JobDetail postScoreRefreshJobDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(postScoreRefreshJobDetail);
        factoryBean.setName("postScoreRefreshTrigger");
        factoryBean.setGroup("communityTriggerGroup");
        factoryBean.setRepeatInterval(1000 * 60 * 5);
        factoryBean.setJobDataMap(new JobDataMap());


        return factoryBean;
    }


    // 定时删log
    @Bean
    public JobDetailFactoryBean deleteLogDetail() {

        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(DeleteLogJob.class);
        factoryBean.setName("deleteLogJob");
        factoryBean.setGroup("deleteLogJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);

        return factoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean deleteLogTrigger(JobDetail deleteLogDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(deleteLogDetail);
        factoryBean.setName("deleteLogTrigger");
        factoryBean.setGroup("deleteLogTriggerGroup");
        factoryBean.setRepeatInterval(1000 * 60 * 60 * 24 * 7);
//        factoryBean.setRepeatInterval(1000);
        factoryBean.setJobDataMap(new JobDataMap());


        return factoryBean;
    }

    // 定时删wk创建的Image
    @Bean
    public JobDetailFactoryBean deleteImageDetail() {

        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(WKImageDeleteJob.class);
        factoryBean.setName("deleteImageJob");
        factoryBean.setGroup("deleteImageJobGroup");
        factoryBean.setDurability(true);
        factoryBean.setRequestsRecovery(true);

        return factoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean deleteImageTrigger(JobDetail deleteImageDetail) {
        SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
        factoryBean.setJobDetail(deleteImageDetail);
        factoryBean.setName("deleteImageTrigger");
        factoryBean.setGroup("deleteImageTriggerGroup");
        factoryBean.setRepeatInterval(1000 * 60 * 60 * 24 * 7);
//        factoryBean.setRepeatInterval(1000 );
        factoryBean.setJobDataMap(new JobDataMap());


        return factoryBean;
    }

}
