package com.ZenBookings.demoZenBookings.application.service;

import com.ZenBookings.demoZenBookings.application.exception.ZenBookingException;
import com.ZenBookings.demoZenBookings.application.mapper.UserMapper;
import com.ZenBookings.demoZenBookings.domain.dto.UserDto;
import com.ZenBookings.demoZenBookings.domain.entity.User;
import com.ZenBookings.demoZenBookings.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        UserDto userDto = new UserDto(1, "test", "test@test.com", "password", null,null, true);
        User user = new User();
        when(userMapper.toEntity(userDto)).thenReturn(user);
        userService.registerUser(userDto);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFindAllUser() throws ZenBookingException {
        User user = new User();
        List<User> userList = Collections.singletonList(user);
        Page<User> userPage = new PageImpl<>(userList);
        UserDto userDto = new UserDto(1, "test", "test@test.com", "password", null,null, true);
        List<UserDto> userDtoList = Collections.singletonList(userDto);
        Pageable pageable = PageRequest.of(0, 1);
        when(userRepository.findAll(pageable)).thenReturn(userPage);
        when(userMapper.toDtoList(userList)).thenReturn(userDtoList);
        userService.findAllUser(0, 1);
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testFindUserById() throws ZenBookingException {
        User user = new User();
        UserDto userDto = new UserDto(1, "test", "test@test.com", "password", null, null,true);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);
        userService.findUserById(1);
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    public void testEditUser() throws ZenBookingException {
        User user = new User();
        UserDto userDto = new UserDto(1, "test", "test@test.com", "password", null, null,true);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        userService.editUser(1, userDto);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testRemoveUser() throws ZenBookingException {
        User user = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        userService.removeUser(1);
        verify(userRepository, times(1)).delete(user);
    }
}
