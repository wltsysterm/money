package com.count.money.core.mvcconfig;

import com.count.money.core.safe.userSafe.AuthorityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by 魏霖涛 on 2017/10/9 0009
 * 自定义拦截器链
 */
@Configuration
public class DefaultMvcConfigure extends WebMvcConfigurerAdapter {
    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new AuthorityInterceptor();
    }
    /**
     * spring boot中可以设置默认首页，当输入域名是可以自动跳转到默认指定的网页
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/html/index.html");//直接去获取项目路径下的html文件夹下的index.html文件
//        registry.addViewController("/").setViewName("forward:/index");//这个表示会去寻找mapped url path 为：【/index】的controller映射
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("*.html")
//                .addResourceLocations("classpath:/html/");
//    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //众多的拦截器组成了一个拦截器链
        /**
         * 主要方法说明：
         * addPathPatterns 用于添加拦截规则
         * excludePathPatterns 用户排除拦截
         */
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/money/**").excludePathPatterns("/money/login","/money/logout","/money/register","/money/reRegister");
//        registry.addInterceptor(new CustomInterceptor2()).addPathPatterns("/*").excludePathPatterns("/");
        super.addInterceptors(registry);
    }
}