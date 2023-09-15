package com.nutricion.dev.infraestructure.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nutricion.dev.domain.patient.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
	
	Page<Patient> findByFirstnameContaining(String name, Pageable pageable);
	Page<Patient> findAll(Pageable pageable);

}
