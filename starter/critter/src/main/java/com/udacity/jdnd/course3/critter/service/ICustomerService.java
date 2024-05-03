package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.model.user.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICustomerService {
    CustomerDTO saveCustomer(CustomerDTO customer);
    CustomerDTO findCustomerByPetId(Long petId);
    List<CustomerDTO> findAllCustomer();
}
