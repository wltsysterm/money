package com.count.money;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@MapperScan("com.count.money.dao")
public class MoneyApplication {
	/**
	 * 修改DispatcherServlet默认配置
	 * @param dispatcherServlet
	 */
	@Bean //@Bean 用在方法上，告诉Spring容器，你可以从下面这个方法中拿到一个Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet);
		registration.getUrlMappings().clear();
		registration.addUrlMappings("/future/*");
		registration.addUrlMappings("/*");
//        registration.addUrlMappings("*.json");
		return registration;
	}
	public static void main(String[] args) {
		SpringApplication.run(MoneyApplication.class, args);
	}
}
