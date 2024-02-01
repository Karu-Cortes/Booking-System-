package com.ZenBookings.demoZenBookings.domain.repository;

import com.ZenBookings.demoZenBookings.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
//devuelve una Página de objetos User. Toma un objeto Pageable como parámetro,
// que normalmente se utiliza para paginación y ordenación.
    Page<User> findAll(Pageable pageable);

    Optional<User> findUserByEmail(String email);
}
