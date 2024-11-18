package com.devops;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.devops.dto.UserDto;
import com.devops.entity.User;
import com.devops.repository.UserRepository;
import com.devops.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    void saveUserTest() {
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);

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
}
