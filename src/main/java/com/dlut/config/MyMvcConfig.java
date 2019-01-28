package com.dlut.config;

import com.dlut.interceptors.DemoInterceptor;
import com.dlut.messageconverter.MyMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.util.List;

@Configuration
/**
 * 必须添加此句否则重写adapter方法无用
 */
@EnableWebMvc
@EnableScheduling
@ComponentScan("com.dlut")
/**
 * 测试配置类
 * @author fys
 * Adapter是什么设计模式
 */
public class MyMvcConfig extends WebMvcConfigurerAdapter
{
    /**
     * <bean id="jsp"  class="org.springframework.web.servlet.view.InternalResourceViewResolver" >
     *         <property name="contentType" value="text/html"/>
     *         <property name="prefix" value="/WEB-INF/"/>
     *         <property name="suffix" value=".jsp"/>
     *     </bean>
     * @return
     */
    @Bean
    public InternalResourceViewResolver viewResolver()
    {
        /**
         * InternalResourceViewResolver 视图解析器
         */
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/classes/views/");
        viewResolver.setSuffix(".jsp");
        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }


    /**
     * 返回一个拦截器
     * @return
     */
    //@Bean
    public DemoInterceptor demoInterceptor()
    {
        return new DemoInterceptor();
    }


    /**
     * 暂时不知道如何使用
     * @return
     */
    @Bean
    public MultipartResolver multipartResolver()
    {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1000000);
        return multipartResolver;
    }


    /**
     * 复写父类中的方法,添加一个拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(demoInterceptor());
    }


    /**
     * 访问静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        //第一个为文件放置目录,第二个为对外暴露的访问路径
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
    }


    /**
     * 访问路径如果配到ww.yy会忽略.后面的路径,通过重写configurePathMatch进行修改
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer)
    {
        configurer.setUseSuffixPatternMatch(false);
    }


    /**
     * 配置页面转向,因为有的跳转没有业务逻辑,只是简单的页面跳转
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry)
    {
        registry.addViewController("/index1").setViewName("index1");
        registry.addViewController("/toUpload").setViewName("upload");
        registry.addViewController("/index2").setViewName("index2");
        registry.addViewController("/converter").setViewName("converter");
        registry.addViewController("/sse").setViewName("sse");
        registry.addViewController("/async").setViewName("async");
    }

    @Bean
    public MyMessageConverter myMessageConverter(){
        return new MyMessageConverter();
    }

    /*public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MyMessageConverter());
    }*/

    //@Override
    //public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    //{
    //    converters.add(new MyMessageConverter());
    //}
}
