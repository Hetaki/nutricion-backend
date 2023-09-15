package com.nutricion.auth.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nutricion.auth.application.bean.AuthRequestBean;
import com.nutricion.auth.application.bean.RegisterRequestBean;
import com.nutricion.auth.service.AuthService;
import com.nutricion.dev.application.bean.ResponseBean;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService service;

	@PostMapping("/register")
	public ResponseEntity<ResponseBean<?>> register(@RequestBody RegisterRequestBean request) {
		return ResponseEntity.ok(service.register(request));
	}

	@PostMapping("/authenticate")
	public ResponseEntity<ResponseBean<?>> authenticate(@RequestBody AuthRequestBean request) {
		return ResponseEntity.ok(service.authenticate(request));
	}

}
