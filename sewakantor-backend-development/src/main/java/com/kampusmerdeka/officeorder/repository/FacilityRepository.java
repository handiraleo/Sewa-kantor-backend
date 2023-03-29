package com.kampusmerdeka.officeorder.repository;

import com.kampusmerdeka.officeorder.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
}