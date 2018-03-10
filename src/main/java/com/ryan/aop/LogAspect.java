package com.ryan.aop;

import com.ryan.log.ASLHLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private static final ObjectMapper JSON = new ObjectMapper();

    @AfterThrowing(
            pointcut = "execution(* com.ryan.*.*(..)) " +
                    "&& !execution(* jerseyConfig*(..))",
            throwing = "error")
    public void applicationConfigLogService(JoinPoint joinPoint, Throwable error) {
        printLog(joinPoint, error);
    }

    private void printLog(JoinPoint joinPoint, Throwable error) {
        ASLHLog log = new ASLHLog(joinPoint.getSignature().getDeclaringType());
        try {
            log.error("Error-msg: " + error.getMessage() +
                            ";Location: " + joinPoint.toShortString() +
                            ";Params: " + JSON.writeValueAsString(joinPoint.getArgs())
                    , error);
        } catch (StackOverflowError | Exception e) {
            log.warn("Log aspect parse params failed. ", e);
            log.error("Error-msg: " + error.getMessage() +
                    ";Location: " + joinPoint.toShortString(), e);
        }
    }

}
