package com.ZenBookings.demoZenBookings.domain.repository;

import com.ZenBookings.demoZenBookings.domain.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Page<Booking> findAll(Pageable pageable);
}
