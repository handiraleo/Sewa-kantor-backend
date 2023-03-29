package com.kampusmerdeka.officeorder.repository;

import com.kampusmerdeka.officeorder.entity.Unit;
import com.kampusmerdeka.officeorder.entity.UnitImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitImageRepository extends JpaRepository<UnitImage, Long> {
    Iterable<UnitImage> findByUnit_Id(Long buildingId);

    Iterable<UnitImage> findByUnit(Unit unit);
}