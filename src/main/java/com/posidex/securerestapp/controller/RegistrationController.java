package com.posidex.securerestapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posidex.securerestapp.dto.UserDetailsDto;
import com.posidex.securerestapp.entity.UserDetails;
import com.posidex.securerestapp.service.RegistrationService;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {
	private static final Logger logger = LogManager.getLogger(RegistrationController.class);
	
    @Autowired
    private RegistrationService service;

    @PostMapping
    public ResponseEntity<UserDetails> register(@RequestBody UserDetailsDto dto) {
    	 logger.info("Received registration request for username: {}", dto.getUsername());
        UserDetails user = service.registerUser(dto);
        logger.info("Registration successful for user id: {}", user.getId());
        return ResponseEntity.ok(user);
    }
}