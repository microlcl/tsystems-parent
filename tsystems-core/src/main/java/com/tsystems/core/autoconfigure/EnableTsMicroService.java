package com.tsystems.core.autoconfigure;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.tsystems.core.config.BeanConfig;
import com.tsystems.core.config.DbRouteAspect;
import com.tsystems.core.config.GlobalExceptionHandlerConfig;
import com.tsystems.core.config.ResponseAdvice;
import com.tsystems.core.config.WebMvcConfig;



@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({BeanConfig.class,
	WebMvcConfig.class,
	ResponseAdvice.class,
	GlobalExceptionHandlerConfig.class,
	DbRouteAspect.class})
public @interface EnableTsMicroService {

	Class<?> superClass() default Void.class;
}
