package com.kampusmerdeka.officeorder.repository;

import com.kampusmerdeka.officeorder.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {
}