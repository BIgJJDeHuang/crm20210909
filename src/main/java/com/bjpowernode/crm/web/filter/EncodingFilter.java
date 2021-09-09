package com.bjpowernode.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("进入到过滤字符编码的过滤器");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        filterChain.doFilter(req,resp);

    }
}
