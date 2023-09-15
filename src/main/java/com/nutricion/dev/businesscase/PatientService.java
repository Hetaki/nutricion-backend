package com.nutricion.dev.businesscase;

import java.util.List;

import com.nutricion.dev.application.bean.ResponseBean;
import com.nutricion.dev.application.bean.patient.PatientBean;

public interface PatientService {
	
	ResponseBean<List<PatientBean>> listPatient(String name, Integer pageNumber, Integer pageSize);

}
