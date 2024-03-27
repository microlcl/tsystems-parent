package com.tsystem.microservice.demo.service;

import com.tsystem.microservice.demo.domain.model.Ping;

public interface PingService {

	Object getPingList();

	void save(Ping p);

}
