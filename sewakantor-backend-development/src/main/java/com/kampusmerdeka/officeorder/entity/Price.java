package com.kampusmerdeka.officeorder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prices")
public class Price extends BaseEntity {

    public enum Type {
        HOURLY("Hour"),
        DAILY("Day"),
        WEEKLY("Week"),
        MONTHLY("Month"),
        YEARLY("Year");

        public String label;

        Type(String label) {
            this.label = label;
        }
    }

    @Enumerated
    private Type type;

    @Column(name = "price")
    private Long price;

    @JoinColumn(name = "unit_id")
    @ManyToOne(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Unit unit;
}
