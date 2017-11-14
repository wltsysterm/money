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
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/money/**").excludePathPatterns("/money/login");
//        registry.addInterceptor(new CustomInterceptor2()).addPathPatterns("/*").excludePathPatterns("/");
        super.addInterceptors(registry);
    }
}

class CustomInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
                                Exception exception) throws Exception {
        //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
        System.out.println("3. 整个请求结束之后被调用......CustomInterceptor1......");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView view)
            throws Exception {
        // 请求处理之后进行调用，但是在视图被渲染之前
        System.out.println("2. 请求处理之后进行调用，但是在视图被渲染之前......CustomInterceptor1......");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        // 在请求处理之前进行调用
        System.out.println("1. 在请求处理之前进行调用......CustomInterceptor1......");
        // 只有返回true才会继续向下执行，返回false取消当前请求
        return true;
    }

}

class CustomInterceptor2 implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
                                Exception exception) throws Exception {
        //在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
        System.out.println("3. 整个请求结束之后被调用......CustomInterceptor2......");
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView view)
            throws Exception {
        // 请求处理之后进行调用，但是在视图被渲染之前
        System.out.println("2. 请求处理之后进行调用，但是在视图被渲染之前......CustomInterceptor2......");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        // 在请求处理之前进行调用
        System.out.println("1. 在请求处理之前进行调用......CustomInterceptor2......");
        // 只有返回true才会继续向下执行，返回false取消当前请求
        String url = request.getRequestURI().toString();
        System.out.println(url);
//        if(!url.matches("/api/\\*")){
//            return false;
//        }
        return true;
    }
}