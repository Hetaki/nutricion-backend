package com.nutricion.auth.application.bean;

import com.nutricion.dev.domain.user.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestBean {

	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private Role role;

}
