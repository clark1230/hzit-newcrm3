package com.hzitshop.test;

import com.hzitshop.Application;
import com.hzitshop.quartz.MyJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by xianyaoji on 2017/3/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)   //1.
@SpringBootTest(classes = Application.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT )
public class TestTask {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    @Test
    public void test() throws SchedulerException {
       /* System.out.println("hello");
        JobDetail jobDetail  = JobBuilder.newJob(MyJob.class)
                .withIdentity("job1","group1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/20 * * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("triger1","group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);*/
    }
    @Test
    public void test2() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();
    }
}
