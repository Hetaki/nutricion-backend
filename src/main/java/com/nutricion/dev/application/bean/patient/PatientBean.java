package com.nutricion.dev.application.bean.patient;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientBean {

	private Integer id;
	private String firstname;
	private String lastname;
	private PatientTypeBean patientType;

}
