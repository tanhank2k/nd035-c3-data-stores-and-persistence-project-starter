package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.common.enums.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.model.user.Employee;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public interface IEmployeeService {
    EmployeeDTO saveEmployee(EmployeeDTO employee);
    List<EmployeeDTO> findEmployeesByService(LocalDate localDate, Set<EmployeeSkill> skillSet);
    EmployeeDTO findById(Long Id);
    void updateEmployeeAvailability(Set<DayOfWeek> days, Long id);
}
