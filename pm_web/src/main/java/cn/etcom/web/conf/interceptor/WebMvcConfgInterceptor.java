package cn.etcom.web.conf.interceptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@Configuration
public class WebMvcConfgInterceptor  implements WebMvcConfigurer{
	
	/**
	 * 拦截器 
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SystemInterceptor())
		        .addPathPatterns("/**")
		        .excludePathPatterns("/user/all");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
		
	/**
	 * 页面跳转
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/usr/add")
		        .setViewName("user/add");
		registry.addViewController("/usr/update")
		        .setViewName("user/update");
		WebMvcConfigurer.super.addViewControllers(registry);
	}
	/**
	 * 静态资源
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
		        .addResourceLocations("classpath:/static");
		WebMvcConfigurer.super.addResourceHandlers(registry); 
	}
	
	/**
	 * 消息转换
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		/***********************springboot2.0*************************************/
	    Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
	    while(iterator.hasNext()){
	        HttpMessageConverter<?> converter = iterator.next();
	        if(converter instanceof MappingJackson2HttpMessageConverter){
	            iterator.remove();
	        }
	    }
	    /***********************springboot2.0*************************************/
		 //1.需要定义一个convert转换消息的对象;
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //2.添加fastJson的配置信息，比如：是否要格式化返回的json数据;
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
		/*
		 * SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
		 * SerializerFeature.WriteNullStringAsEmpty,
		 * SerializerFeature.DisableCircularReferenceDetect,
		 * SerializerFeature.WriteNullListAsEmpty,
		 * SerializerFeature.WriteDateUseDateFormat
		 */		
        		SerializerFeature.PrettyFormat,
        		SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteEnumUsingToString,
                /*SerializerFeature.WriteMapNullValue,*/
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect    
                );
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        //3处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        //4.在convert中添加配置信息.
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        //5.将convert添加到converters当中.
        converters.add(fastJsonHttpMessageConverter);
        WebMvcConfigurer.super.configureMessageConverters(converters); 
	}
}
