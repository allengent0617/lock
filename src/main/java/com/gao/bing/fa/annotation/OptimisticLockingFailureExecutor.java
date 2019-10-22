package com.gao.bing.fa.annotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.dao.OptimisticLockingFailureException;

/**
 * @author : allengent@163.com
 * @date : 2019/10/22 14:34
 * description :
 */
@Aspect
@Configuration
@Slf4j
public class OptimisticLockingFailureExecutor implements Ordered {
    private static final int    DEFAULT_MAX_RETRIES = 100;

    private int                 maxRetries          = DEFAULT_MAX_RETRIES;
    private int                 order               = 1;

    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public int getOrder() {
        return this.order;
    }

    @Pointcut("@annotation(RetryOnOptimisticLockingFailure)")
    public void retryOnOptFailure() {
        // pointcut mark
    }

    @Around("retryOnOptFailure()")
    public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
        int numAttempts = 0;
        do {
            numAttempts++;
            try {
                Object ob= pjp.proceed();
                log.info(" 第{}  次  重试 执行成功 ！" ,numAttempts);
                return ob;

            } catch (OptimisticLockingFailureException ex) {
               // log.error(" 第{}  次  执行失败 ！" ,numAttempts);
                if (numAttempts > maxRetries) {
                    //log failure information, and throw exception
                    throw ex;
                }else{
                    //log failure information for audit/reference
                    //will try recovery
                   // log.error(" 第{}  次  retry ！" ,numAttempts);
                }
            }
        } while (numAttempts <= this.maxRetries);
       // log.error(" 第{}  次  执行成功 ！" ,numAttempts);

        return null;
    }
}