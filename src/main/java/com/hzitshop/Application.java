package com.hzitshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@MapperScan("com.hzitshop.mapper*")
/*@ImportResource(locations = "classpath:application-shiro.xml")*/
@SpringBootApplication
public class Application extends SpringBootServletInitializer{

	/**
	 * <p>
	 * 测试 RUN
	 * <br>
	 * 1、http://localhost:8080/user/test1<br>
	 * 2、http://localhost:8080/user/test2<br>
	 * </p>
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * 使用线程池!!
	 * @return
	 */
	@Bean
	public ThreadPoolTaskExecutor createThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(10);
		threadPoolTaskExecutor.setMaxPoolSize(20);
		return threadPoolTaskExecutor;
	}
}
