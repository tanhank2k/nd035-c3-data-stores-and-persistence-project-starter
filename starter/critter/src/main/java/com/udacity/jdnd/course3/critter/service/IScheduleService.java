package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.model.schedule.Schedule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IScheduleService {
    ScheduleDTO saveSchedule(ScheduleDTO schedule);
    List<ScheduleDTO> findAllSchedules();
    List<ScheduleDTO> findByEmployeeId(Long employeeId);
    List<ScheduleDTO> findByPetId(Long petId);
    List<ScheduleDTO> findByCustomerId(Long customerId);

}
