package com.nutricion.dev.application.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(Include.NON_NULL)
public class ResponseBean<T> {

	private String status;
	private Integer statusCode;
	private T data;
	private Error error;
	
	
	@Data
	@Builder
	@JsonInclude(Include.NON_NULL)
	public static class Error {
		private String errorMessage;
		private String errorCode;
	}

}
