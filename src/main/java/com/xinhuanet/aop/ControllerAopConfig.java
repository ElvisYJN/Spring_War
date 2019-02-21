package com.xinhuanet.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * Aop
 */

@Aspect
@Configuration
public class ControllerAopConfig {

    private static final Logger log = Logger.getLogger(ControllerAopConfig.class);

    /*//开始时间
    ThreadLocal<Long> startTimes = new ThreadLocal<>();
    // map1 存放方法被调用的次数
    ThreadLocal<Map<String, Long>> map1 = new ThreadLocal<>();
    //map2存放方法总耗时
    ThreadLocal<Map<String, Long>> map2 = new ThreadLocal<>();*/

    @Pointcut("execution(* com.xinhuanet.controller..* (..))")
    public void weblog() {

    }

    @Before("weblog()")
    public void doBefore(JoinPoint point) throws Throwable {
        log.info("前置通知");

        //接收请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP_METHOD : " + request.getMethod());
        log.info("IP : " + request.getRemoteAddr());
        log.info("CLASS_METHOD : " + point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
        log.info("ARGS : " + Arrays.toString(point.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "weblog()")
    public void doAfterReturning(Object ret) throws Throwable {
        log.info("后置通知");
        log.info("response:" + ret);
    }

    @Around("weblog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("进入AOP切面");
        log.info("环绕通知");
        /*if (map1.get()==null){
            map1.set(new HashMap<>(6));
        }
        if (map2.get()==null){
            map2.set(new HashMap<>(6));
        }*/
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            log.info("环绕通知");
            //如果切到了 没有返回类型的void方法，这里直接返回
            if (result == null) {
                return null;
            }
            long end = System.currentTimeMillis();
            String targetClassName = joinPoint.getSignature().getDeclaringTypeName();
            String MethodName = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            int argsSize = args.length;
            String typeStr = joinPoint.getSignature().getDeclaringType().toString().split(" ")[0];
            String returnType = joinPoint.getSignature().toString().split(" ")[0];
            for (Object arg : args) {
                log.info(arg.getClass().getTypeName() + ": " + arg.toString());
            }
            log.info("arg长度:" + argsSize + "");
            log.info(typeStr);
            log.info(returnType);
            log.info(result.toString());
            log.info("执行方法--耗时:" + (end - start) + "ms");
            return result;

        } catch (Exception e) {
            log.info("AOP 异常：", e);
        }

        return null;
    }


}
