package com.tsystem.microservice.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tsystem.microservice.demo.domain.model.Ping;
import com.tsystem.microservice.demo.repository.PingRepository;
import com.tsystem.microservice.demo.service.PingService;



@Service
public class PingServiceImpl implements PingService {
	
	@Autowired
	private PingRepository pingRepository;

	@Override
	public Object getPingList() {
		// TODO Auto-generated method stub
		return pingRepository.getPingList();
	}

	@Override
	public void save(Ping p) {
		pingRepository.save(p);
		
	}

}
