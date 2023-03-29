package com.kampusmerdeka.officeorder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "units")
public class Unit extends BaseEntity {

    public enum Type {
        OFFICE_ROOM("Office Room"),
        COWORKING("Coworking"),
        MEETING_ROOM("Meeting Room"),
        VIRTUAL_ROOM("Virtual Room");

        public String label;

        Type(String label) {
            this.label = label;
        }
    }

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "length", precision = 1)
    private Double length;

    @Column(name = "width", precision = 1)
    private Double width;

    @Column(name = "height", precision = 1)
    private Double height;

    @Enumerated
    private Type type;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "building_id")
    private Building building;

    @OneToMany(mappedBy = "unit", targetEntity = Review.class, fetch = FetchType.LAZY)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "unit", targetEntity = Price.class, fetch = FetchType.LAZY)
    private Set<Price> prices;

}
