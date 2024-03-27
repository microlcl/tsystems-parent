package com.tsystems.core.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.tsystems.core.db.DatabaseContextHolder;
import com.tsystems.core.db.DbRoute;


@Aspect
public class DbRouteAspect {
	@Pointcut("@annotation(com.tsystems.core.db.DbRoute)")
	public void pointcutDeclaration() {}

	@Before(value="pointcutDeclaration()&&@annotation(dbRoute)",argNames="dbRoute")
	public void beforePointCut(DbRoute dbRoute) {
		DatabaseContextHolder.setDatabaseType(dbRoute.value().getSimpleName());
	}

	@After(value="pointcutDeclaration()")
	public void afterPointCut() {
		DatabaseContextHolder.clearDatabaseType();
	}
}
