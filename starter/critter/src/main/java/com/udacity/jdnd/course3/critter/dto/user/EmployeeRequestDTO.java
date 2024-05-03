package com.udacity.jdnd.course3.critter.dto.user;


import com.udacity.jdnd.course3.critter.common.enums.user.EmployeeSkill;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a request to find available employees by skills. Does not map
 * to the database directly.
 */
@Getter
@Setter
public class EmployeeRequestDTO {
    private Set<EmployeeSkill> skills;
    private LocalDate date;
}
