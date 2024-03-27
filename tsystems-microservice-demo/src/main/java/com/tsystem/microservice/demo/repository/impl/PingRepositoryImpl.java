package com.tsystem.microservice.demo.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tsystem.microservice.demo.domain.model.Ping;
import com.tsystem.microservice.demo.repository.PingRepository;
import com.tsystem.microservice.demo.repository.db.mapper.PingMapper;



@Repository
public class PingRepositoryImpl implements PingRepository {
	
	@Autowired(required = false)
	private PingMapper pingMapper;

	@Override
	public Object getPingList() {

		return pingMapper.getPingList();
	}

	@Override
	public void save(Ping p) {
		pingMapper.save(p);
		
	}

}
