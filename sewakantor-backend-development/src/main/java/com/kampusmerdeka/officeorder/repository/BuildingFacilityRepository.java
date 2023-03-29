package com.kampusmerdeka.officeorder.repository;

import com.kampusmerdeka.officeorder.entity.Building;
import com.kampusmerdeka.officeorder.entity.BuildingFacility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingFacilityRepository extends JpaRepository<BuildingFacility, Long> {
    Iterable<BuildingFacility> findByBuilding(Building building);
}