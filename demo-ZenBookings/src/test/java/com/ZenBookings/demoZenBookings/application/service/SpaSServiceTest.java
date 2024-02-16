package com.ZenBookings.demoZenBookings.application.service;
import com.ZenBookings.demoZenBookings.application.exception.ZenBookingException;
import com.ZenBookings.demoZenBookings.application.mapper.SpaServiceMapper;
import com.ZenBookings.demoZenBookings.domain.dto.SpaServiceDto;
import com.ZenBookings.demoZenBookings.domain.entity.SpaService;
import com.ZenBookings.demoZenBookings.domain.repository.SpaServiceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class SpaSServiceTest {
    @Mock
    private SpaServiceRepository spaRepository;

    @Mock
    private SpaServiceMapper spaMapper;

    @InjectMocks
    private SpaSService spaSService;

    @Test
    public void registerSpaTest() {
        SpaServiceDto spaDto = new SpaServiceDto(1, "name", "description","url");
        SpaService spa = new SpaService();
        when(spaMapper.toEntity(spaDto)).thenReturn(spa);
        spaSService.registerSpa(spaDto);
        verify(spaMapper).toEntity(spaDto);
        verify(spaRepository).save(spa);
    }

    @Test
    public void findAllSpaTest() throws ZenBookingException {

        Integer offset = 0;
        Integer limit = 5;
        Pageable pageable = PageRequest.of(offset, limit);
        List<SpaService> spaList = new ArrayList<>();
        spaList.add(new SpaService());
        Page<SpaService> spaPage = new PageImpl<>(spaList);
        when(spaRepository.findAll(pageable)).thenReturn(spaPage);
        List<SpaServiceDto> spaDtoList = new ArrayList<>();
        spaDtoList.add(new SpaServiceDto(1, "name", "description","url"));
        when(spaMapper.toDtoList(spaList)).thenReturn(spaDtoList);
        List<SpaServiceDto> result = spaSService.findAllSpa(offset, limit);
        verify(spaRepository).findAll(pageable);
        verify(spaMapper).toDtoList(spaList);
        assertEquals(spaDtoList, result);
    }
    @Test
    public void findSpaByIdTest() throws ZenBookingException {

        Integer id = 1;
        SpaService spa = new SpaService();
        when(spaRepository.findById(id)).thenReturn(Optional.of(spa));
        SpaServiceDto spaDto = new SpaServiceDto(1, "name", "description","url");
        when(spaMapper.toDto(spa)).thenReturn(spaDto);
        SpaServiceDto result = spaSService.findSpaById(id);
        verify(spaRepository).findById(id);
        verify(spaMapper).toDto(spa);
        assertEquals(spaDto, result);
    }
    @Test
    public void editSpaTest() throws ZenBookingException {

        Integer id = 1;
        SpaServiceDto spaDto = new SpaServiceDto(1, "new name", "new description","url");
        SpaService existingSpa = new SpaService();
        when(spaRepository.findById(id)).thenReturn(Optional.of(existingSpa));
        spaSService.editSpa(id, spaDto);
        verify(spaRepository).findById(id);
        assertEquals(spaDto.name(), existingSpa.getName());
        assertEquals(spaDto.description(), existingSpa.getDescription());
        verify(spaRepository).save(existingSpa);
    }
    @Test
    public void removeSpaTest() throws ZenBookingException {

        Integer id = 1;
        SpaService spa = new SpaService();
        when(spaRepository.findById(id)).thenReturn(Optional.of(spa));
        spaSService.removeSpa(id);
        verify(spaRepository).findById(id);
        verify(spaRepository).delete(spa);
    }
}
