package com.kampusmerdeka.officeorder.repository;

import com.kampusmerdeka.officeorder.entity.Price;
import com.kampusmerdeka.officeorder.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceRepository extends JpaRepository<Price, Long> {
    Optional<Price> findFirst1ByUnit(Unit unit);
}