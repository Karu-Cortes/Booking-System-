package com.ZenBookings.demoZenBookings.application.controller;
import com.ZenBookings.demoZenBookings.application.exception.ZenBookingException;
import com.ZenBookings.demoZenBookings.application.service.SpaSService;
import com.ZenBookings.demoZenBookings.domain.dto.SpaServiceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class SpaSControllerTest {

    @InjectMocks
    private SpaSController spaSController;

    @Mock
    private SpaSService spaSService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterSpa() {
        SpaServiceDto spaDto = new SpaServiceDto(1, "Test Spa", "Test Description");
        doNothing().when(spaSService).registerSpa(spaDto);
        ResponseEntity<?> response = spaSController.registerSpa(spaDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(spaSService, times(1)).registerSpa(spaDto);
    }

    @Test
    public void testFindAllSpa() throws ZenBookingException {
        SpaServiceDto spaDto = new SpaServiceDto(1, "Test Spa", "Test Description");
        List<SpaServiceDto> spaList = Collections.singletonList(spaDto);
        when(spaSService.findAllSpa(0, 10)).thenReturn(spaList);
        ResponseEntity<?> response = spaSController.findAllSpa(0, 10);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(spaList, response.getBody());
        verify(spaSService, times(1)).findAllSpa(0, 10);
    }
    @Test
    public void testFindSpaById() throws ZenBookingException {
        SpaServiceDto spaDto = new SpaServiceDto(1, "Test Spa", "Test Description");
        when(spaSService.findSpaById(1)).thenReturn(spaDto);
        ResponseEntity<?> response = spaSController.findSpaById(1);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(spaDto, response.getBody());
        verify(spaSService, times(1)).findSpaById(1);
    }

    @Test
    public void testEditSpa() throws ZenBookingException {
        SpaServiceDto spaDto = new SpaServiceDto(1, "Test Spa", "Test Description");
        doNothing().when(spaSService).editSpa(1, spaDto);
        ResponseEntity<?> response = spaSController.editSpa(1, spaDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(spaSService, times(1)).editSpa(1, spaDto);
    }

    @Test
    public void testRemoveSpa() throws ZenBookingException {
        doNothing().when(spaSService).removeSpa(1);
        ResponseEntity<?> response = spaSController.removeSpa(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(spaSService, times(1)).removeSpa(1);
    }
}
