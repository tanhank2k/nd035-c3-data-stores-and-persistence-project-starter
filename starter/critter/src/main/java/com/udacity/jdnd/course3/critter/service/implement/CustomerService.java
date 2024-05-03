package com.udacity.jdnd.course3.critter.service.implement;

import com.udacity.jdnd.course3.critter.dto.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.model.pet.Pet;
import com.udacity.jdnd.course3.critter.model.user.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService implements ICustomerService {
    @Autowired
    PetRepository petRepository;
    @Autowired
    CustomerRepository customerRepository;

    private CustomerDTO convertToDto(Customer customer) {
        CustomerDTO result = CustomerDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .notes(customer.getNotes())
                .phoneNumber(customer.getPhoneNumber())
                .build();
        if (Objects.nonNull(customer.getPets())) {
            List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
            result.setPetIds(petIds);
        }
        return result;
    }

    private Customer convertDtoToModel(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setNotes(customerDTO.getNotes());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        return customer;
    }


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDto) {
        Customer customer = convertDtoToModel(customerDto);

        List<Long> petIds = customerDto.getPetIds();
        if (Objects.nonNull(petIds) && !petIds.isEmpty()) {
            List<Pet> pets = petRepository.findAllById(petIds);
            customer.setPets(pets);
        }
        Customer result = customerRepository.save(customer);

        return convertToDto(result);
    }

    @Override
    public CustomerDTO findCustomerByPetId(Long petId) {
        Pet pet = petRepository.getOne(petId);
        Customer customer = customerRepository.findById(pet.getCustomer().getId()).get();
        return convertToDto(pet.getCustomer());
    }

    @Override
    public List<CustomerDTO> findAllCustomer() {
        return customerRepository.findAll().stream().map((item) -> convertToDto(item)).collect(Collectors.toList());
    }
}
