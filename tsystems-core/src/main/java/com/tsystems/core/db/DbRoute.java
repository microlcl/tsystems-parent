package com.tsystems.core.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target; 

@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD) 
public @interface DbRoute {
	
	
	public abstract Class<? extends DB> value();

	public static class DB {
	}
	
	public static final class MASTER extends DB {
	};

	public static final class SLAVE extends DB {
	};
}
