package com.figure.core.filter;

import javax.servlet.*;
import java.io.IOException;


public class EncodeFilter implements Filter {

    private final String encode = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 对request和response进行一些预处理
        request.setCharacterEncoding(encode);
        response.setCharacterEncoding(encode);
        response.setContentType("text/html;charset="+encode);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}