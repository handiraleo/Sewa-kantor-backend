package com.kampusmerdeka.officeorder.service;

import com.kampusmerdeka.officeorder.dto.repsonse.TourResponse;
import com.kampusmerdeka.officeorder.dto.request.TourRequest;
import com.kampusmerdeka.officeorder.entity.Building;
import com.kampusmerdeka.officeorder.entity.Tour;
import com.kampusmerdeka.officeorder.entity.Unit;
import com.kampusmerdeka.officeorder.entity.UserCustomer;
import com.kampusmerdeka.officeorder.repository.BuildingRepository;
import com.kampusmerdeka.officeorder.repository.TourRepository;
import com.kampusmerdeka.officeorder.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class TourService {
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private AuthService authService;

    public ResponseEntity<Object> create(TourRequest request) {
        Optional<Building> buildingOptional = buildingRepository.findById(request.getBuildingId());
        if (buildingOptional.isEmpty()) return ResponseUtil.notFound("building not found");

        LocalTime time = LocalTime.parse(request.getTime(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate startDate = LocalDate.parse(request.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(request.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate now = LocalDate.now(ZoneId.systemDefault());

        if (startDate.isBefore(now)) return ResponseUtil.badRequest("start date has passed");
        if (endDate.isBefore(startDate)) return ResponseUtil.badRequest("end date is after start date");

        UserCustomer me = authService.me();
        Building building = buildingOptional.get();
        Tour tour = Tour.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .companyName(request.getCompanyName())
                .CompanyEmail(request.getCompanyEmail())
                .startDate(startDate)
                .endDate(endDate)
                .time(time)
                .duration(request.getDuration())
                .building(building)
                .customer(me)
                .unitType(Unit.Type.valueOf(request.getUnitType()))
                .build();

        tour = tourRepository.saveAndFlush(tour);

        return ResponseUtil.ok("tour schedule created successfully",
                TourResponse.builder()
                        .id(tour.getCreatedAt())
                        .name(tour.getName())
                        .phone(tour.getPhone())
                        .companyName(tour.getCompanyName())
                        .companyEmail(tour.getCompanyEmail())
                        .startDate(tour.getStartDate().toString())
                        .endDate(tour.getEndDate().toString())
                        .time(tour.getTime().toString())
                        .buildingId(tour.getBuilding().getId())
                        .building(tour.getBuilding().getName())
                        .duration(tour.getDuration())
                        .unitType(tour.getUnitType())
                        .build()
        );
    }
}
