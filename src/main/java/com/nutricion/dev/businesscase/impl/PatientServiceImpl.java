package com.nutricion.dev.businesscase.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nutricion.dev.application.bean.ResponseBean;
import com.nutricion.dev.application.bean.patient.PatientBean;
import com.nutricion.dev.application.bean.patient.PatientTypeBean;
import com.nutricion.dev.businesscase.PatientService;
import com.nutricion.dev.domain.patient.Patient;
import com.nutricion.dev.infraestructure.repository.PatientRepository;
import com.nutricion.dev.shared.ResponseUtil;
import com.nutricion.dev.shared.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
	
	private final PatientRepository patientRepository;

	@Override
	public ResponseBean<List<PatientBean>> listPatient(String name, Integer pageNumber, Integer pageSize) {

		Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);
		Page<Patient> patients;
		if (!Utils.isEmpty(name)) {
			patients = patientRepository.findByFirstnameContaining(name, pageable);
		} else {
			patients = patientRepository.findAll(pageable);
		}
		
		List<PatientBean> data = new ArrayList<>();
		if (!Utils.isEmpty(patients)) {
			patients.get().forEach(p -> {
				PatientBean bean = PatientBean.builder()
									.id(p.getId())
									.firstname(p.getFirstname())
									.lastname(p.getLastname())
									.patientType(PatientTypeBean.builder()
														.id(p.getPatientType().getId())
														.description(p.getPatientType().getDescription())
														.build())
									.build();
				data.add(bean);
			});
		}
		
		return ResponseUtil.ok(data);
	}

	

}
