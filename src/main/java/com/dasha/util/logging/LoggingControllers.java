package com.dasha.util.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Slf4j
@Component
@ConditionalOnProperty(prefix = "logging", name = "controllers")
public class LoggingControllers {
    @Autowired
    private HttpServletRequest request;

    @Pointcut("@annotation(ApiLogging)")
    public void loggingControllerPointcut() {}

    @AfterThrowing(pointcut = "loggingControllerPointcut()", throwing = "e")
    public void recordItemNotFoundException(JoinPoint joinPoint, Throwable e) {
        log.error("Method " + joinPoint.getSourceLocation().getWithinType().getName() + "." + joinPoint.getSignature().getName() + " threw exception: " + e.getMessage());
    }

    @Around(value = "loggingControllerPointcut()")
    public Object recordApiExecuting(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        try {
            result = joinPoint.proceed();
            String logString = formLogString(joinPoint);
            log.info(logString);
        } catch (Throwable e) {
            throw e;
        }
        return result;
    }

    private String formLogString(ProceedingJoinPoint joinPoint) {
        String api = request.getServletPath();
        String ip = getIp();
        String methodParams = getMethodParams(joinPoint);
        return String.format("api: %s,\tip: %s, \tparametrs: %s", api, ip, methodParams);
    }

    private String getIp() {
        String remoteAddr = request.getHeader("X-FORWARDED-FOR");
        if (remoteAddr == null || "".equals(remoteAddr)) {
            remoteAddr = request.getRemoteAddr();
        }
        return remoteAddr;
    }

    private String getMethodParams(ProceedingJoinPoint joinPoint) {
        Object[] params = joinPoint.getArgs();
        LocalVariableTableParameterNameDiscoverer nameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] p = nameDiscoverer.getParameterNames(((MethodSignature) joinPoint.getSignature()).getMethod());
        StringBuilder s = new StringBuilder();
        if (params != null && p != null) {
            for (int i = 0; i < params.length; i++) {
                s.append("\t ")
                 .append(p[i])
                 .append(": ")
                 .append(params[i]);
            }
        }
        return s.toString();
    }

}
