package com.kampusmerdeka.officeorder.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(value = "ADMIN")
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
@Table(name = "admin")
public class UserAdmin extends User {

    @Column(name = "username", unique = true, nullable = false)
    private String username;

}
