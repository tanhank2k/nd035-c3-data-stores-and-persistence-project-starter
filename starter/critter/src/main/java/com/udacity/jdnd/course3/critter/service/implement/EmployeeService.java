package com.udacity.jdnd.course3.critter.service.implement;

import com.udacity.jdnd.course3.critter.common.enums.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.dto.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.model.user.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService implements IEmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    private Employee convertDtoToModel(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }

    private EmployeeDTO convertToDto(Employee employee) {
        return EmployeeDTO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .skills(employee.getSkills())
                .daysAvailable(employee.getDaysAvailable())
                .build();
    }


    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDto) {
        Employee employee = convertDtoToModel(employeeDto);
        return convertToDto(employeeRepository.save(employee));
    }

    @Override
    public List<EmployeeDTO> findEmployeesByService(LocalDate localDate, Set<EmployeeSkill> skillSet) {
        List<Employee> employees = employeeRepository.findAllByDaysAvailableContains(localDate.getDayOfWeek());
        List<Employee> result = employees.stream().filter((item) -> item.getSkills().containsAll(skillSet))
                .collect(Collectors.toList());
        return result
                .stream().map((item) -> convertToDto(item)).collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO findById(Long id) {
        return convertToDto(employeeRepository.findById(id).get());
    }

    @Override
    public void updateEmployeeAvailability(Set<DayOfWeek> days, Long id) {
        Employee employee = employeeRepository.findById(id).get();
        employee.setDaysAvailable(days);
        employeeRepository.save(employee);
    }
}
