package com.lgd.home.springaop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 *
 */
@Aspect
//@Component
public class AspectLgd {

    /**
     * 切点
     */
    @Pointcut("execution(* com.lgd.home.service.*.*(..)))")
    private void pointcut(){

    }

    //环绕增强
    @Around("com.lgd.home.springaop.AspectLgd.pointcut()")
    public Object around(ProceedingJoinPoint pjp){
        long current=System.currentTimeMillis();
        Object proceed=null;
        try {
            proceed = pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long end=System.currentTimeMillis();
        System.out.println("执行时间为："+(end-current));
        return proceed;
    }

    //前置增强
    @Before("com.lgd.home.springaop.AspectLgd.pointcut()")
    public void before(){
        System.out.println("before");
    }
    //后置增强
    @After("com.lgd.home.springaop.AspectLgd.pointcut()")
    public void after(){
        System.out.println("after");
    }
}
