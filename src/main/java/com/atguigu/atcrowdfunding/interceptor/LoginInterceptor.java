package com.atguigu.atcrowdfunding.interceptor;

import com.atguigu.atcrowdfunding.bean.User;
import com.atguigu.atcrowdfunding.util.Const;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //定义一些需要放行的路径
        Set<String> uri = new HashSet<String>();
        uri.add("/user/reg.do");
        uri.add("/user/reg.htm");
        uri.add("/login.htm");
        uri.add("/doLogin.do");
        uri.add("/logout.do");
        //获取请求路径
        String path = request.getServletPath();
        if (uri.contains(path)){
            //放行
            return true;
        }
        //检查是否登陆
        User user = (User) request.getSession().getAttribute(Const.LOGIN_USER);
        if (user!=null){
            //已经登陆放行
            return true;
        }
        //没有登陆，重定向到登陆页面
        response.sendRedirect(request.getContextPath()+"/login.htm");
        return false;
    }
}
