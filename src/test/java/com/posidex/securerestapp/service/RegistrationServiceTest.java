package com.posidex.securerestapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.posidex.securerestapp.dto.UserDetailsDto;
import com.posidex.securerestapp.entity.UserDetails;
import com.posidex.securerestapp.repository.UserDetailsRepository;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private UserDetailsRepository userDetailsRepo;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    public void testRegisterUserSuccess() {
        UserDetailsDto dto = new UserDetailsDto();
        dto.setUsername("testuser");
        dto.setEmail("test@example.com");
        dto.setPassword("password123");
        dto.setSecurityQuestion("Your pet's name?");
        dto.setSecurityAnswer("Fluffy");

        UserDetails savedUser = new UserDetails();
        savedUser.setId(1L);
        savedUser.setUsername(dto.getUsername());
        savedUser.setEmail(dto.getEmail());

        when(userDetailsRepo.save(any(UserDetails.class))).thenReturn(savedUser);

        UserDetails result = registrationService.registerUser(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(dto.getUsername(), result.getUsername());

        verify(userDetailsRepo, times(1)).save(any(UserDetails.class));
    }
}