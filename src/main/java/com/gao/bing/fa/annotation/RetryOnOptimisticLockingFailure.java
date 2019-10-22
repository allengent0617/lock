package com.gao.bing.fa.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author : allengent@163.com
 * @date : 2019/10/22 14:33
 * description :
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryOnOptimisticLockingFailure {

}