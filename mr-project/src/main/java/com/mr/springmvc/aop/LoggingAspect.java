//package com.mr.springmvc.aop;
//
//import java.util.Arrays;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.apache.log4j.Logger;
//
//@Aspect
//public class LoggingAspect {
//
//	private Logger logger = Logger.getLogger(LoggingAspect.class);
//
//	@Around("execution(* com.mr.springmvc.service.*.*(..))")
//	public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {
//
//		logger.info("logAround() is running!");
//		logger.info("hijacked method : " + joinPoint.getSignature().getName());
//		logger.info("hijacked arguments : " + Arrays.toString(joinPoint.getArgs()));
//
//		logger.info("Around before is running!");
//		joinPoint.proceed(); // continue on the intercepted method
//		logger.info("Around after is running!");
//
//		logger.info("******");
//
//	}
//
//}
