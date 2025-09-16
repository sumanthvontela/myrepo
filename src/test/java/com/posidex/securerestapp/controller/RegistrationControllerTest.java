package com.posidex.securerestapp.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.posidex.securerestapp.dto.UserDetailsDto;
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
    public void testUpdateUser() throws Exception {
        Long userId = 1L;
        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername("updatedUser");
        dto.setEmail("updated@mail.com");
        when(registrationService.updateUser(eq(userId), any(UserDetailsDto.class))).thenReturn(dto);

        mockMvc.perform(put("/api/users/{id}", userId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("updatedUser"))
                .andExpect(jsonPath("$.email").value("updated@mail.com"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        Long userId = 1L;
        doNothing().when(registrationService).deleteUser(userId);

        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully!"));
    }
}