package com.count.money.core.safe.userSafe;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorityInterceptor implements HandlerInterceptor
{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		SessionData sessionData = loginAuth(request);
		AppContext.putSession(sessionData);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
	{

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
	{

	}

	/**
	 * 登录验证
	 * @param request
	 */
	private SessionData loginAuth(HttpServletRequest request) throws Exception{
//		SessionData sessionData = JSON.parseObject(JedisUtil.get(token), SessionData.class);
//		String name =
		SessionData sessionData = new SessionData();
		if(sessionData==null){
			throw new Exception("登入信息失效");
		}
		return null;
	}

    /**
     * 去除访问地址串中多余的的斜杠，防止绕过过滤器
     * @param uri
     * @return
     */
    private String formatRequestURI(String uri) {
        String[] s = uri.split("/");
        StringBuffer result = new StringBuffer();
        for (String c : s) {
            if (!"".equals(c.trim()))
                result.append("/").append(c);
        }
        return result.toString();
    }

    private String getRequestURI(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        requestURI = this.formatRequestURI(requestURI); // 格式化请求URI
        if (requestURI.indexOf("/api/") >= 0) {
            requestURI = requestURI.substring(requestURI.indexOf("/api/"));
        }
        return requestURI;
    }
}

