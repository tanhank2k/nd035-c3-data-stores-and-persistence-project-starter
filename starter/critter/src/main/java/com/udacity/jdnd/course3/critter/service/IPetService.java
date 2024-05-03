package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.pet.PetDTO;
import com.udacity.jdnd.course3.critter.model.pet.Pet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPetService {
    PetDTO savePet(PetDTO pet);
    List<PetDTO> findByCustomerId (Long customerId);
    List<PetDTO> findAllPets ();
    PetDTO findById(Long id);
}
