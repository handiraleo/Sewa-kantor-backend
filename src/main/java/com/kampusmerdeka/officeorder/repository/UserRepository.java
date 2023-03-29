package com.kampusmerdeka.officeorder.repository;

import com.kampusmerdeka.officeorder.entity.UserAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAdmin, Long> {
    Optional<UserAdmin> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Long id);
}