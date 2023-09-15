package com.nutricion.auth.service;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nutricion.auth.application.bean.AuthRequestBean;
import com.nutricion.auth.application.bean.AuthResponseBean;
import com.nutricion.auth.application.bean.RegisterRequestBean;
import com.nutricion.dev.application.bean.ResponseBean;
import com.nutricion.dev.domain.token.Token;
import com.nutricion.dev.domain.token.TokenType;
import com.nutricion.dev.domain.user.User;
import com.nutricion.dev.infraestructure.repository.TokenRepository;
import com.nutricion.dev.infraestructure.repository.UserRepository;
import com.nutricion.dev.shared.ResponseUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final TokenRepository tokenRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public ResponseBean<AuthResponseBean> register(RegisterRequestBean request) {
		var user = User
					.builder()
					.firstname(request.getFirstname())
					.lastname(request.getLastname())
					.username(request.getUsername())
					.password(passwordEncoder.encode(request.getPassword()))
					.role(request.getRole())
					.createdDate(new Date())
					.createdBy("AUTOMATIC")
					.status("1")
					.build();
		var savedUser = userRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		this.saveUserToken(savedUser, jwtToken);

		var authResponse = AuthResponseBean
							.builder()
							.accessToken(jwtToken)
							.refreshToken(refreshToken)
							.build();

		return ResponseUtil.ok(authResponse);
	}

	public ResponseBean<AuthResponseBean> authenticate(AuthRequestBean request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(user);
		revokeAllUserTokens(user);
		saveUserToken(user, jwtToken);

		var authResponse = AuthResponseBean
							.builder()
							.accessToken(jwtToken)
							.refreshToken(refreshToken)
							.build();

		return ResponseUtil.ok(authResponse);
	}

	/***
	 * 
	 * @param user
	 * @param jwtToken
	 */
	private void saveUserToken(User user, String jwtToken) {
		var token = Token.builder()
					.user(user)
					.token(jwtToken)
					.tokenType(TokenType.BEARER)
					.expired(false)
					.revoked(false)
					.build();
		tokenRepository.save(token);
	}

	/**
	 * 
	 * @param user
	 */
	private void revokeAllUserTokens(User user) {
		var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
		if (validUserTokens.isEmpty())
			return;
		validUserTokens.forEach(token -> {
			token.setExpired(true);
			token.setRevoked(true);
		});
		tokenRepository.saveAll(validUserTokens);
	}

}
