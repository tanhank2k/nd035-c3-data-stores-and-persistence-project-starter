package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.model.pet.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("Select p From Pet p where p.customer.id = :id")
    List<Pet> findByCustomerId(Long id);
}
