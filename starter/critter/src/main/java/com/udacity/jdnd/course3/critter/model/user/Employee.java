package com.udacity.jdnd.course3.critter.model.user;

import com.udacity.jdnd.course3.critter.common.enums.user.EmployeeSkill;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */
@Getter
@Setter
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Nationalized
    @Column
    private String name;

    @Column
    @CollectionTable(name = "employee_skill",
            joinColumns = @JoinColumn(name = "employee_id"))
    @ElementCollection
    private Set<EmployeeSkill> skills;

    @Column
    @CollectionTable(name = "employee_days",
            joinColumns = @JoinColumn(name = "employee_id"))
    @ElementCollection
    private Set<DayOfWeek> daysAvailable;

}
