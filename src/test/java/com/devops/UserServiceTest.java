package com.devops;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.devops.dto.UserDto;
import com.devops.entity.User;
import com.devops.repository.UserRepository;
import com.devops.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class UserServiceTest {
	UserRepository userRepository = mock(UserRepository.class);
	UserService userService = new UserService(userRepository);

    @Test
    void saveUserTest() {

        UserDto userDto = new UserDto();
        userDto.setName("John Doe");
        userDto.setEmail("john@example.com");

        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john@example.com");

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User savedUser = userService.saveUser(userDto);

        assertEquals("John Doe", savedUser.getName());
        assertEquals("john@example.com", savedUser.getEmail());
        verify(userRepository, times(1)).save(Mockito.any(User.class));
    }
    @Test
    void testGetAllUsers() {
        // Mock data
        List<User> mockUsers = Arrays.asList(
                new User() {{ setId(1L); setName("John Doe"); setEmail("john@example.com"); }},
                new User() {{ setId(2L); setName("Jane Doe"); setEmail("jane@example.com"); }}
        );
        when(userRepository.findAll()).thenReturn(mockUsers);

        // Execute
        List<User> users = userService.getAllUsers();

        // Assert
        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById_Found() {
        // Mock data
        User mockUser = new User() {{ setId(1L); setName("John Doe"); setEmail("john@example.com"); }};
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        System.out.println("---------¨^^^bienvenu aux tests unitaires ------------^^^^^^----------");

        // Execute
        User user = userService.getUserById(1L);

        // Assert
        assertNotNull(user);
        assertEquals("John Doe", user.getName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        // Mock repository behavior
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Execute & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUserById(1L));
        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }
}
