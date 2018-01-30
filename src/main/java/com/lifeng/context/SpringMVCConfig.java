package com.lifeng.context;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.lifeng.intercept.Oauth2AccessTokenAnnotationInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.lifeng.web", useDefaultFilters = false, includeFilters = @Filter(Controller.class))
public class SpringMVCConfig extends WebMvcConfigurerAdapter {

	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		RequestMappingHandlerAdapter adapter = new org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter();

		List<MediaType> mediaList = new ArrayList<MediaType>();
		mediaList.add(new MediaType("application", "json", Charset.forName("UTF-8")));
		mediaList.add(new MediaType("text", "plain", Charset.forName("UTF-8")));
		mediaList.add(new MediaType("text", "html", Charset.forName("UTF-8")));
		mediaList.add(new MediaType("text", "xml", Charset.forName("UTF-8")));

		StringHttpMessageConverter stringHttpMessageConverter = new org.springframework.http.converter.StringHttpMessageConverter();
		stringHttpMessageConverter.setSupportedMediaTypes(mediaList);

		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new org.springframework.http.converter.json.MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(mediaList);

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(stringHttpMessageConverter);
		messageConverters.add(mappingJackson2HttpMessageConverter);

		adapter.setMessageConverters(messageConverters);
		return adapter;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/images/**").addResourceLocations("/images/");
		super.addResourceHandlers(registry);
	}

	@Bean
	public InternalResourceViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
		exceptionResolver.setDefaultErrorView("error/error");
		return exceptionResolver;
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(simpleMappingExceptionResolver());
		super.configureHandlerExceptionResolvers(exceptionResolvers);
	}

	@Bean
	public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
		SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
		Properties mappings = new Properties();
		mappings.put("/**", simpleForwardHttpRequestHandlerForToHTM());
		simpleUrlHandlerMapping.setMappings(mappings);
		return simpleUrlHandlerMapping;
	}

	@Bean
	public SimpleForwardHttpRequestHandlerForToHTM simpleForwardHttpRequestHandlerForToHTM() {
		return new SimpleForwardHttpRequestHandlerForToHTM();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new Oauth2AccessTokenAnnotationInterceptor()).addPathPatterns("/wx/**");

		super.addInterceptors(registry);
	}

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver() {

		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		// commonsMultipartResolver.setMaxUploadSize(30*1024*1024);
		return commonsMultipartResolver;
	}
}
