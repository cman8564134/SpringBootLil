package com.cthye.lil.spring101.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CountingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountingAspect.class);
    private static final Map<String, Integer> countMap = new HashMap();

    @Pointcut("@annotation(LogMethodCount)")
    public void executeLogMethodCount(){}

    @Before("executeLogMethodCount()")
    public void beforeExecuteLogMethodCount(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().toLongString();
        if (countMap.containsKey(methodName)){
            countMap.put(methodName, countMap.get(methodName) + 1);
        }else {
            countMap.put(methodName, 1);
        }
        LOGGER.info("BEFORE method: " + methodName + ";count: " + countMap.get(methodName));
    }

    @AfterReturning(value = "executeLogMethodCount()", returning = "returnValue")
    public void afterReturningExecuteLogMethodCount(JoinPoint joinPoint, Object returnValue){
        String methodName = joinPoint.getSignature().toLongString();
        LOGGER.info("AFTER RETURN method: " + methodName + ";returnValue: " + returnValue.toString());
    }

    @Around(value = "executeLogMethodCount()")
    public Object aroundExecuteLogMethodCount(ProceedingJoinPoint proceedingJoinPoint){
        String methodName = proceedingJoinPoint.getSignature().toLongString();
        Object returnValue = null;
        try {
            returnValue = proceedingJoinPoint.proceed();
            LOGGER.info("AROUND method: " + methodName + ";returnValue: " + returnValue.toString());
        } catch (Throwable throwable) {
            LOGGER.error("AROUND method: " + methodName, throwable );
        }
        return returnValue;
    }

}
