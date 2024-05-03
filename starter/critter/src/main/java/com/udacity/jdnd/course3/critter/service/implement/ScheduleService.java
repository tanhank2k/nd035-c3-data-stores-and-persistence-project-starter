package com.udacity.jdnd.course3.critter.service.implement;

import com.udacity.jdnd.course3.critter.dto.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.model.pet.Pet;
import com.udacity.jdnd.course3.critter.model.schedule.Schedule;
import com.udacity.jdnd.course3.critter.model.user.Customer;
import com.udacity.jdnd.course3.critter.model.user.Employee;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService implements IScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;
    @Autowired
    PetRepository petRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CustomerRepository customerRepository;

    private ScheduleDTO convertToDto(Schedule schedule) {
        List<Employee> employees = schedule.getEmployees();
        List<Pet> pets = schedule.getPets();
        ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                .id(schedule.getId())
                .activities(schedule.getActivities())
                .date(schedule.getDate())
                .petIds(schedule.getPets().stream().map(Pet::getId).collect(Collectors.toList()))
                .build();
        if (Objects.nonNull(employees) && !employees.isEmpty()) {
            scheduleDTO.setEmployeeIds(employees.stream().map(Employee::getId).collect(Collectors.toList()));
        }
        if (Objects.nonNull(pets) && !pets.isEmpty()) {
            scheduleDTO.setPetIds(pets.stream().map(Pet::getId).collect(Collectors.toList()));
        }
        return scheduleDTO;
    }

    private Schedule convertDtoToModel(ScheduleDTO scheduleDto) {
        List<Long> employeeIds = scheduleDto.getEmployeeIds();
        List<Long> petIds = scheduleDto.getPetIds();
        Schedule schedule = new Schedule();
        schedule.setId(scheduleDto.getId());
        schedule.setActivities(scheduleDto.getActivities());
        schedule.setDate(scheduleDto.getDate());

        if (Objects.nonNull(employeeIds) && employeeIds.size() > 0) {
            List<Employee> employees = employeeRepository.findAllById(employeeIds);
            schedule.setEmployees(employees);
        }
        if (Objects.nonNull(petIds) && petIds.size() > 0) {
            List<Pet> pets = petRepository.findAllById(petIds);
            schedule.setPets(pets);
        }
        return schedule;
    }


    @Override
    public ScheduleDTO saveSchedule(ScheduleDTO scheduleDto) {
        Schedule schedule = convertDtoToModel(scheduleDto);
        return convertToDto(scheduleRepository.save(schedule));
    }

    @Override
    public List<ScheduleDTO> findAllSchedules() {
        return scheduleRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> findByEmployeeId(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).get();
        return scheduleRepository.findSchedulesByEmployees(employee)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> findByPetId(Long petId) {
        Pet pet = petRepository.findById(petId).get();
        return scheduleRepository.findSchedulesByPets(pet)
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDTO> findByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        return scheduleRepository.findSchedulesByPetsIn(customer.getPets().stream().collect(Collectors.toList()))
                .stream().map(this::convertToDto).collect(Collectors.toList());
    }
}
