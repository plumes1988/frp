package com.figure.core.aop;

import com.figure.core.service.log.ILogUserActionService;
import com.figure.core.service.sys.impl.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Aspect
@Component
public class UserActionLogAop {

    @Autowired
    private ILogUserActionService logUserActionService;

    @Autowired
    AuthService authService;

    /**
     * <p> 【环绕通知】 用于拦截指定方法，获取用户操作日志 <p>
     *
     *      定义切入点表达式： execution(public * (…))
     *      表达式解释： execution：主体    public:可省略   *：标识方法的任意返回值  任意包+类+方法(…) 任意参数
     *
     *      com.figure.*.controller ： 标识AOP所切服务的包名，即需要进行横切的业务类
     *      .*Controller ： 标识类名，*即所有类
     *      .*(..) ： 标识任何方法名，括号表示参数，两个点表示任何参数类型
     *
     * @param pjp：切入点对象
     * @param apiOperation:api注解对象
     * @return: java.lang.Object
     */
    public static Map<String,String> apiInstructionsMap= new HashMap();

    @Around("(execution(* com.figure.*.controller.*Controller.*(..)) || execution(* com.figure.*.controller.*.*Controller.*(..))) && @annotation(apiOperation)")
    public Object doAround(ProceedingJoinPoint pjp, ApiOperation apiOperation) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        log.debug("请求地址："+request.getRequestURI());
        apiInstructionsMap.put(request.getRequestURI()+":"+request.getMethod(),apiOperation.value());
        return pjp.proceed();
    }

}
