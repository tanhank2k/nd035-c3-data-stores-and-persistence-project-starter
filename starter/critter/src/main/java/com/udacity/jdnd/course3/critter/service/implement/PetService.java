package com.udacity.jdnd.course3.critter.service.implement;

import com.udacity.jdnd.course3.critter.dto.pet.PetDTO;
import com.udacity.jdnd.course3.critter.model.pet.Pet;
import com.udacity.jdnd.course3.critter.model.user.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.IPetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PetService implements IPetService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    private PetDTO convertToDTO(Pet pet) {
        return PetDTO.builder()
                .id(pet.getId())
                .type(pet.getType())
                .name(pet.getName())
                .birthDate(pet.getBirthDate())
                .ownerId(pet.getCustomer().getId())
                .build();
    }

    private Pet convertDtoToModel(PetDTO petDto) {
        Optional<Customer> customer = customerRepository.findById(petDto.getOwnerId());
        Pet pet = new Pet();
        pet.setId(petDto.getId());
        pet.setType(petDto.getType());
        pet.setName(petDto.getName());
        pet.setBirthDate(petDto.getBirthDate());
        pet.setCustomer(customer.orElse(null));

        return pet;
    }

    @Override
    public PetDTO savePet(PetDTO petDto) {
        Pet pet = convertDtoToModel(petDto);
        pet = petRepository.save(pet);
        Customer customer = pet.getCustomer();
        customer.setPets(new ArrayList<>(Arrays.asList(pet)));
        customerRepository.save(customer);
        return convertToDTO(pet);
    }

    @Override
    public List<PetDTO> findByCustomerId(Long customerId) {
        return petRepository.findByCustomerId(customerId)
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<PetDTO> findAllPets() {
        return petRepository.findAll()
                .stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public PetDTO findById(Long id) {
        return convertToDTO(petRepository.findById(id).get());
    }
}
