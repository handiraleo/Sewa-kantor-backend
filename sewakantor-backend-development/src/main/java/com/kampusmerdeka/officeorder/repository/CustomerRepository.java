package com.kampusmerdeka.officeorder.repository;

import com.kampusmerdeka.officeorder.entity.UserCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<UserCustomer, Long> {
    Optional<UserCustomer> findByEmail(String email);
}