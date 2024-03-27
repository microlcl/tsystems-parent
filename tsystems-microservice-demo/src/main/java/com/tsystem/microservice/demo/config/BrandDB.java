package com.tsystem.microservice.demo.config;

import com.tsystems.core.db.DbRoute;

/**
 * 
 * @author Lichunlei
 *
 */
public class BrandDB{
	  private BrandDB() {
		    throw new IllegalStateException("Utility class");
		  }

	public static final class MASTER extends DbRoute.DB{}; //NOSONAR
	public static final class ITADMIN extends DbRoute.DB{};  //NOSONAR
	
}
