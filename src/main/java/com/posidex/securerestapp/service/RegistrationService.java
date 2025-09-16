package com.posidex.securerestapp.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.posidex.securerestapp.dto.UserDetailsDto;
import com.posidex.securerestapp.entity.UserCredentials;
import com.posidex.securerestapp.entity.UserDetails;
import com.posidex.securerestapp.repository.UserDetailsRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RegistrationService {
	
	private static final Logger logger = LogManager.getLogger(RegistrationService.class);

    @Autowired
    private UserDetailsRepository userDetailsRepo;

    public UserDetails registerUser(UserDetailsDto dto) {
    	logger.info("Registering user with username: {}", dto.getUsername());
        UserDetails details = new UserDetails();
        details.setUsername(dto.getUsername());
        details.setEmail(dto.getEmail());

        UserCredentials credentials = new UserCredentials();
        credentials.setPassword(dto.getPassword()); 
        credentials.setSecurityQuestion(dto.getSecurityQuestion());
        credentials.setSecurityAnswer(dto.getSecurityAnswer());
        credentials.setUserDetails(details);

        details.setCredentials(credentials);
        logger.info("User registered with id: {}", details.getId());
        
        return userDetailsRepo.save(details);
    }
    

    // Update user
    public UserDetailsDto updateUser(Long id, UserDetailsDto dto) {
        UserDetails user = userDetailsRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
       
        if (user.getCredentials() != null && dto.getPassword() != null) {
            user.getCredentials().setPassword(dto.getPassword());
            user.getCredentials().setSecurityQuestion(dto.getSecurityQuestion());
            user.getCredentials().setSecurityAnswer(dto.getSecurityAnswer());
        }
        userDetailsRepo.save(user);
        logger.info("User updated with id: {}", id);
        return dto;  //
    }

 
    public void deleteUser(Long id) {
        if (!userDetailsRepo.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        userDetailsRepo.deleteById(id);
        logger.info("User deleted with id: {}", id);
    }
}