package com.kampusmerdeka.officeorder.repository;

import com.kampusmerdeka.officeorder.entity.Building;
import com.kampusmerdeka.officeorder.entity.BuildingImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingImageRepository extends JpaRepository<BuildingImage, Long> {
    Iterable<BuildingImage> findByBuilding(Building building);

    Iterable<BuildingImage> findByBuilding_Id(Long buildingId);
}