package com.kampusmerdeka.officeorder.repository;

import com.kampusmerdeka.officeorder.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}