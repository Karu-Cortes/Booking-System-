package com.ZenBookings.demoZenBookings.domain.repository;

import com.ZenBookings.demoZenBookings.domain.entity.SpaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaServiceRepository extends JpaRepository<SpaService, Integer> {

    Page<SpaService> findAll(Pageable pageable);
}
