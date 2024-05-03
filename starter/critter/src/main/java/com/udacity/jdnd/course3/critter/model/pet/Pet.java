package com.udacity.jdnd.course3.critter.model.pet;

import com.udacity.jdnd.course3.critter.common.enums.pet.PetType;
import com.udacity.jdnd.course3.critter.model.schedule.Schedule;
import com.udacity.jdnd.course3.critter.model.user.Customer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column
    @Enumerated(EnumType.STRING)
    private PetType type;

    @Nationalized
    @Column
    private String name;

    @Column
    @Type(type = "LocalDate")
    private LocalDate birthDate;

    @Column
    private String notes;

    @ManyToOne(targetEntity = Customer.class)
    private Customer customer;

    @ManyToMany(mappedBy = "pets")
    private List<Schedule> schedules;

}
