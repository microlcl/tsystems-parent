package com.tsystem.microservice.demo.repository;

import com.tsystem.microservice.demo.domain.model.Ping;

public interface PingRepository {

	Object getPingList();

	void save(Ping p);

}
