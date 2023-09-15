package com.nutricion.dev.application.bean.patient;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientTypeBean {

	private Integer id;
	private String description;
	
}
