package com.posidex.securerestapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posidex.securerestapp.dto.UserDetailsDto;
import com.posidex.securerestapp.entity.UserDetails;
import com.posidex.securerestapp.service.RegistrationService;

@RestController
@RequestMapping("/api/users")
public class RegistrationController {
    private static final Logger logger = LogManager.getLogger(RegistrationController.class);
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<UserDetails> register(@RequestBody UserDetailsDto dto) {
        logger.info("Received registration request for username: {}", dto.getUsername());
        UserDetails user = registrationService.registerUser(dto);
        logger.info("Registration successful for user id: {}", user.getId());
        return ResponseEntity.ok(user);
    }

   
    @PutMapping("/{id}")
    public ResponseEntity<UserDetailsDto> updateUser(@PathVariable Long id, @RequestBody UserDetailsDto dto) {
        logger.info("Update request for user id: {}", id);
        UserDetailsDto updatedUser = registrationService.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        logger.info("Delete request for user id: {}", id);
        registrationService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }
}