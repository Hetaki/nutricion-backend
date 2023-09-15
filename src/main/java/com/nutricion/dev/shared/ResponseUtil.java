package com.nutricion.dev.shared;

import org.springframework.http.HttpStatus;

import com.nutricion.dev.application.bean.ResponseBean;

public class ResponseUtil {

	public static <T> ResponseBean<T> ok(T data) {
		return generic(HttpStatus.OK, data);
	}

	public static <T> ResponseBean<T> error403() {
		var error = ResponseBean.Error.builder()
				.errorCode("ERR001")
				.errorMessage("Acceso restringido")
				.build();
		return generic(HttpStatus.FORBIDDEN, error);
	}
	
	private static <T> ResponseBean<T> generic(HttpStatus status, T data) {
		return generic(status, data, null);
	}

	private static <T> ResponseBean<T> generic(HttpStatus status, ResponseBean.Error error) {
		return generic(status, null, error);
	}

	private static <T> ResponseBean<T> generic(HttpStatus status, T data, ResponseBean.Error error) {
		return ResponseBean.
				<T>builder()
				.statusCode(status.value())
				.status(status.getReasonPhrase())
				.error(error)
				.data(data)
				.build();
	}
	
}
