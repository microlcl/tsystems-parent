package com.tsystem.microservice.demo.domain.model;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author lcl
 *
 */
@Alias("Ping")
@JsonInclude(value=Include.NON_EMPTY)
//@JsonIgnoreProperties(value = {"imgType","linkId","modelId","areaRange"})
public class Ping {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4137897659802461964L;

	/**
	 * 实验ID
	 */
	private Long id;

	/**
	 * 实验名称
	 */
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
