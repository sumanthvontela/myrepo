package com.posidex.securerestapp.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.posidex.securerestapp.dto.UserDetailsDto;
import com.posidex.securerestapp.entity.UserCredentials;
import com.posidex.securerestapp.entity.UserDetails;
import com.posidex.securerestapp.repository.UserDetailsRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {
    @Mock
    private UserDetailsRepository userDetailsRepo;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    public void testUpdateUser_Success() {
        Long userId = 1L;
        UserDetails existing = new UserDetails();
        existing.setId(userId);
        existing.setUsername("oldUser");
        existing.setEmail("old@mail.com");
        UserCredentials creds = new UserCredentials();
        existing.setCredentials(creds);

        UserDetailsDto updateDto = new UserDetailsDto();
        updateDto.setUsername("newUser");
        updateDto.setEmail("new@mail.com");
        updateDto.setPassword("newPass");
        updateDto.setSecurityQuestion("q1");
        updateDto.setSecurityAnswer("a1");

        when(userDetailsRepo.findById(userId)).thenReturn(Optional.of(existing));
        when(userDetailsRepo.save(any(UserDetails.class))).thenReturn(existing);

        UserDetailsDto result = registrationService.updateUser(userId, updateDto);
        assertEquals("newUser", result.getUsername());
        assertEquals("new@mail.com", result.getEmail());
        verify(userDetailsRepo, times(1)).save(any(UserDetails.class));
    }

    @Test
    public void testDeleteUser_Success() {
        Long userId = 1L;
        when(userDetailsRepo.existsById(userId)).thenReturn(true);
        doNothing().when(userDetailsRepo).deleteById(userId);
        assertDoesNotThrow(() -> registrationService.deleteUser(userId));
        verify(userDetailsRepo, times(1)).deleteById(userId);
    }

    @Test
    public void testDeleteUser_NotFound() {
        Long userId = 2L;
        when(userDetailsRepo.existsById(userId)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> registrationService.deleteUser(userId));
    }
}