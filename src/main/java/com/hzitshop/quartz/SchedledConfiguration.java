package com.hzitshop.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by xianyaoji on 2017/3/7.
 */
@Configuration
@EnableScheduling
public class SchedledConfiguration {
   /* @Bean
    public Scheduler scheduler() throws IOException, SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory(quartzProperties());
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.start();
        return scheduler;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        return schedulerFactoryBean;
    }
    

    *//**
     * 设置quartz属性
     * @throws IOException
     *//*
    public Properties quartzProperties() throws IOException {
        Properties prop = new Properties();
        prop.put("quartz.scheduler.instanceName", "ServerScheduler");
        prop.put("description","我的作业");
        prop.put("org.quartz.threadPool.threadCount", "8");
        return prop;
    }*/
    /**
     * 配置拦截器
     *
     * @param registry
     */
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns(
//				"/**");
//	}

   /* @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }*/

    /**调度工厂bean
     * @param
     * @return
     * @throws IOException
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("cronJobTrigger") Trigger cronJobTrigger) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //注册触发器
        factory.setTriggers(cronJobTrigger);

        return factory;
    }

    /**加载quartz数据源配置,quartz集群时用到
     * @return
     * @throws IOException
     */
//	    @Bean
//	    public Properties quartzProperties() throws IOException {
//	        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//	        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
//	        propertiesFactoryBean.afterPropertiesSet();
//	        return propertiesFactoryBean.getObject();
//	    }

    /**加载触发器
     * @return
     */
    @Bean
    public JobDetailFactoryBean sampleJobDetail() {
        return createJobDetail(MyJob.class);
    }

    /**加载定时器
     * @param jobDetail
     * @param frequency
     * @return
     */
    @Bean(name = "cronJobTrigger")
    public CronTriggerFactoryBean sampleJobTrigger(@Qualifier("sampleJobDetail") JobDetail jobDetail,
                                                   @Value("${samplejob.frequency}") long frequency) {
        return createTrigger(jobDetail, frequency);
    }

    /**创建触发器工厂
     * @param jobClass
     * @return
     */
    private static JobDetailFactoryBean createJobDetail(Class jobClass) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(true);
        return factoryBean;
    }

    /**创建一个以频率为触发节点,以毫秒为单位,可以指定每隔x秒执行任务
     * @param jobDetail
     * @param pollFrequencyMs
     * @return

    private static SimpleTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs) {
    SimpleTriggerFactoryBean factoryBean = new SimpleTriggerFactoryBean();
    factoryBean.setJobDetail(jobDetail);
    factoryBean.setStartDelay(0L);
    factoryBean.setRepeatInterval(pollFrequencyMs);
    factoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
    // in case of misfire, ignore all missed triggers and continue :
    factoryBean.setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NEXT_WITH_REMAINING_COUNT);
    return factoryBean;
    }*/

    /**创建定时器工厂
     * @param jobDetail
     * @param pollFrequencyMs
     * @return
     */
    private static CronTriggerFactoryBean createTrigger(JobDetail jobDetail, long pollFrequencyMs) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        //factoryBean.setCronExpression ("0/5 * * * * ? ");//每5秒执行一次
        factoryBean.setCronExpression("0 0/5 8-20 * * ?"); //每天 8 9 10 ...20进行定时作业
        return factoryBean;
    }

}
