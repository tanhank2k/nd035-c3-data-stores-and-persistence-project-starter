package com.udacity.jdnd.course3.critter.model.user;

import com.udacity.jdnd.course3.critter.model.pet.Pet;
import com.udacity.jdnd.course3.critter.model.schedule.Schedule;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.List;

/**
 * Represents the form that customer request and response data takes. Does not map
 * to the database directly.
 */
@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Nationalized
    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column
    private String notes;

    @OneToMany(targetEntity = Pet.class)
    private List<Pet> pets;

    @ManyToMany
    private List<Schedule> schedules;
}
