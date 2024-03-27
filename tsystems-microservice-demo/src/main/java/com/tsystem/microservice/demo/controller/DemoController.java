package com.tsystem.microservice.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsystem.microservice.demo.service.PingService;
import com.tsystems.core.api.ResponseData;
import com.tsystems.core.exception.ApiRuntimeException;


@RestController
@RequestMapping("/demo")
public class DemoController {
	
	@Autowired
	private PingService pingService;
	
	@GetMapping("/echo")
	public Object echo() {
		Map<String, Object> result = new HashMap<>();
		result.put("name", "li");
		result.put("age", 5);
		return result;
	}
	
	@PostMapping("/echo")
	public ResponseData<Map> echoPost(@RequestBody Map<String, Object> req) throws InterruptedException {
		return ResponseData.success(req);
	}
	
	@GetMapping("/exception")
	public ResponseData echo(String s) {
		if("a".equalsIgnoreCase(s)) {
			throw new ApiRuntimeException("a will throw a exception");
		}
		return ResponseData.success(s);
	}
	
	@GetMapping("/ping")
	public ResponseData getPing() {
		return ResponseData.success(pingService.getPingList());
	}

}
