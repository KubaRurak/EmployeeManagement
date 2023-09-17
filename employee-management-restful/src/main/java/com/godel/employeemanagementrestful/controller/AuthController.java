package com.godel.employeemanagementrestful.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godel.employeemanagementrestful.dto.AuthResponseDTO;
import com.godel.employeemanagementrestful.dto.LoginDTO;
import com.godel.employeemanagementrestful.security.JWTGenerator;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JWTGenerator jwtGenerator;
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginDTO loginDTO) {
	    Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(loginDTO.getEmailId(), loginDTO.getPassword()));

	    SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
	}
}
