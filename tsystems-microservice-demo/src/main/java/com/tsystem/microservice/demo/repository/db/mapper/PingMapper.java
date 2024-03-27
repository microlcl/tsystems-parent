package com.tsystem.microservice.demo.repository.db.mapper;

import java.util.List;

import com.tsystem.microservice.demo.config.BrandDB;
import com.tsystem.microservice.demo.domain.model.Ping;
import com.tsystems.core.db.DbRoute;


public interface PingMapper {	

	@DbRoute(BrandDB.MASTER.class)
	List<Ping> getPingList();

	@DbRoute(BrandDB.MASTER.class)
	void save(Ping p);
		
	}
