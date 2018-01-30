package com.lifeng.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;

import com.lifeng.mybatis.MyBatisConfig;

@Configuration
@EnableAspectJAutoProxy
@EnableScheduling
@Import({ MyBatisConfig.class, DataSourceConfig.class })
@ComponentScan(basePackages = "com.lifeng", excludeFilters = { @Filter(Controller.class),
		@Filter(Configuration.class) })
public class SpringRootConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

//	@Bean
//	public Validator validator() {
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//		return factory.getValidator();
//	}
}
