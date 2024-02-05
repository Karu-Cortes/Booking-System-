package com.ZenBookings.demoZenBookings.application.controller;
import com.ZenBookings.demoZenBookings.application.exception.ZenBookingException;
import com.ZenBookings.demoZenBookings.application.lasting.ERole;
import com.ZenBookings.demoZenBookings.application.service.UserService;
import com.ZenBookings.demoZenBookings.domain.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void testRegisterUser() {
        UserDto userDto = new UserDto(1, "Test User", "test@test.com", "password",
                ERole.USER, true);
        doNothing().when(userService).registerUser(userDto);
        ResponseEntity<?> responseEntity = userController.registerUser(userDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testFindAllUser() throws ZenBookingException {

        List<UserDto> userList = new ArrayList<>();
        userList.add(new UserDto(1, "Test User1", "test1@test.com", "password1", ERole.USER, true));
        userList.add(new UserDto(2, "Test User2", "test2@test.com", "password2", ERole.USER, true));
        when(userService.findAllUser(anyInt(), anyInt())).thenReturn(userList);
        ResponseEntity<?> responseEntity = userController.findAllUser(0, 2);
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals(userList, responseEntity.getBody());
    }
    @Test
    public void testFindUserById() throws ZenBookingException {
        UserDto userDto = new UserDto(1, "Test User", "test@test.com", "password", ERole.USER, true);
        when(userService.findUserById(anyInt())).thenReturn(userDto);
        ResponseEntity<?> responseEntity = userController.findUserById(1);
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
        assertEquals(userDto, responseEntity.getBody());
    }

    @Test
    public void testEditUser() throws ZenBookingException {
        UserDto userDto = new UserDto(1, "Test User", "test@test.com", "password", ERole.USER, true);
        doNothing().when(userService).editUser(anyInt(), any(UserDto.class));
        ResponseEntity<?> responseEntity = userController.editUser(1, userDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testRemoveUser() throws ZenBookingException {
        doNothing().when(userService).removeUser(anyInt());
        ResponseEntity<?> responseEntity = userController.removeUser(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}