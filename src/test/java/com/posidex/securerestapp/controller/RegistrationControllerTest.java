package com.posidex.securerestapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posidex.securerestapp.dto.UserDetailsDto;
import com.posidex.securerestapp.entity.UserDetails;
import com.posidex.securerestapp.service.RegistrationService;

@WebMvcTest(RegistrationController.class)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterUser() throws Exception {
        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername("testuser");
        dto.setEmail("test@example.com");
        dto.setPassword("password123");
        dto.setSecurityQuestion("Your pet's name?");
        dto.setSecurityAnswer("Fluffy");

        UserDetails userDetails = new UserDetails();
        userDetails.setId(1L);
        userDetails.setUsername(dto.getUsername());
        userDetails.setEmail(dto.getEmail());

        when(registrationService.registerUser(any(UserDetailsDto.class))).thenReturn(userDetails);

        mockMvc.perform(post("/api/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.username").value("testuser"));

        verify(registrationService, times(1)).registerUser(any(UserDetailsDto.class));
    }
}