package com.figure.core.filter;

import com.figure.core.aop.UserActionLogAop;
import com.figure.core.model.log.LogUserAction;
import com.figure.core.model.sys.SysUserInfo;
import com.figure.core.security.config.WebSecurityConfig;
import com.figure.core.service.log.ILogUserActionService;
import com.figure.core.service.sys.impl.AuthService;
import com.figure.core.util.DateUtils;
import com.figure.core.util.copycat.util.UrlMatchesTheUrlSetUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LogFilter implements Filter {


    @Autowired
    private ILogUserActionService logUserActionService;

    @Autowired
    AuthService authService;

    //无需认证的开放接口
    private final static int ENDPOINT_WITHOUT_AUTHENTICATION = -1;

    //未知用户
    private final static int UNKNOWN_USER = -2;


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        Long startTime = System.currentTimeMillis();
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        System.out.println("认证：Authorization......" + request.getRequestURL());

        List<String> authenticationList = WebSecurityConfig.authenticationList;


        final String authorizationHeader = request.getHeader("Authorization");
        log.info("authorizationHeader==============>" + authorizationHeader);

        LogUserAction logUserAction = null;

        String content  = "";

        String substring = "";

        // 判断是否为预检请求
        if ("OPTIONS".equals(request.getMethod()) && request.getHeader("Access-Control-Request-Method") != null) {
            // 是预检请求，进行相应的处理
            // ...
        } else {
            // 不是预检请求，执行其他逻辑
            logUserAction = new LogUserAction();
            substring = request.getRequestURI().substring(4);
            String ipAddress = request.getRemoteAddr();
            if("0:0:0:0:0:0:0:1".equals(ipAddress)){
                ipAddress="127.0.0.1";
            }

            logUserAction.setIp(ipAddress);
            logUserAction.setOptTime(new Date());

            logUserAction.setEndpoint(request.getRequestURI());
        }

        chain.doFilter(req, res);

        if(logUserAction!=null) {
            int status = response.getStatus();
            Long endTime = System.currentTimeMillis();
            Long duration = endTime - startTime;
            logUserAction.setTime(duration.intValue());
            logUserAction.setState(String.valueOf(status));
            content = UserActionLogAop.apiInstructionsMap.get(request.getRequestURI() + ":" + request.getMethod());
            logUserAction.setDescribes(content);

        }

        if (authorizationHeader!=null) {
            String jwt = authorizationHeader.substring(7);
            SysUserInfo tUserInfo = authService.getUserInfoFromReids(jwt);
            if(logUserAction!=null){
                logUserAction.setUserId(tUserInfo.getUserId().intValue());
            }else{
                logUserAction.setUserId(UNKNOWN_USER);
            }
        }else if (UrlMatchesTheUrlSetUtils.matches(substring,authenticationList)) {
            if(logUserAction!=null) {
                logUserAction.setUserId(ENDPOINT_WITHOUT_AUTHENTICATION);
            }
        }else {
            if(logUserAction!=null) {
                logUserAction.setUserId(UNKNOWN_USER);
            }
        }

        if(logUserAction!=null) {
            logUserActionService.save(logUserAction);
        }


    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}
