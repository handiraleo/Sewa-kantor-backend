package com.kampusmerdeka.officeorder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "buildings")
public class Building extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "complex_id")
    private Complex complex;

    @OneToMany(mappedBy = "building", targetEntity = BuildingFacility.class, fetch = FetchType.LAZY)
    private Set<BuildingFacility> buildingFacilities;

    @OneToMany(mappedBy = "building", targetEntity = BuildingImage.class, fetch = FetchType.LAZY)
    private Set<BuildingImage> buildingImages;

    @OneToMany(mappedBy = "building", targetEntity = Unit.class, fetch = FetchType.LAZY)
    private Set<Unit> units;

    @OneToMany(mappedBy = "building", targetEntity = NearbyPlace.class, fetch = FetchType.LAZY)
    private Set<NearbyPlace> nearbyPlaces;

}
