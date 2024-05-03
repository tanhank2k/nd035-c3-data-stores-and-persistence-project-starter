package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.model.pet.Pet;
import com.udacity.jdnd.course3.critter.model.schedule.Schedule;
import com.udacity.jdnd.course3.critter.model.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findSchedulesByPets(Pet pet);

    List<Schedule> findSchedulesByEmployees(Employee employee);

    List<Schedule> findSchedulesByPetsIn(List<Pet> pets);
}
