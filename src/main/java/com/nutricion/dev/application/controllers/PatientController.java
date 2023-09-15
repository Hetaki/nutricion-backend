package com.nutricion.dev.application.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nutricion.dev.application.bean.ResponseBean;
import com.nutricion.dev.businesscase.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/patient")
@RequiredArgsConstructor
public class PatientController {
	
	private final PatientService patientService;
	
	@GetMapping
	public ResponseEntity<ResponseBean<?>> patients(
			@RequestParam(required = false) String name,
			@RequestParam(required = false, defaultValue="0", name="page") Integer page,
			@RequestParam(required = false, defaultValue="50", name="pageSize") Integer pageSize) {
		return ResponseEntity.ok(patientService.listPatient(name, page, pageSize));
	}
	
}
