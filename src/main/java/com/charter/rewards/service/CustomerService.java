package com.charter.rewards.service;

import com.charter.customer.rewards.domain.Customer;
import com.charter.customer.rewards.exception.ResourceNotFoundException;
import com.charter.customer.rewards.repos.CustomerRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;

    public Customer getCustomerById(Long customerId) {
        return customerRepo.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found for customerId: "+ customerId));
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

}
