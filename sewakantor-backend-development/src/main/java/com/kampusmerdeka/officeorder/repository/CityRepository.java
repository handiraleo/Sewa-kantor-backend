package com.kampusmerdeka.officeorder.repository;

import com.kampusmerdeka.officeorder.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}