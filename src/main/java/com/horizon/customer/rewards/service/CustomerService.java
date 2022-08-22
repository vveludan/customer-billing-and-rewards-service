package com.horizon.customer.rewards.service;

import com.horizon.customer.rewards.domain.Customer;
import com.horizon.customer.rewards.exception.ResourceAlreadyExistsException;
import com.horizon.customer.rewards.exception.ResourceNotFoundException;
import com.horizon.customer.rewards.repos.CustomerRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public Customer createCustomer(Customer inputCustomer) {
        Optional<Customer> customerAlreadyExists = customerRepo.findById(inputCustomer.getId());
        if(customerAlreadyExists.isPresent()) {
            throw new ResourceAlreadyExistsException("Customer already exists with Customer Id: "+ inputCustomer.getId());
        }

        Customer customerToBeSaved = Customer.builder()
                .id(inputCustomer.getId())
                .firstName(inputCustomer.getFirstName())
                .lastName(inputCustomer.getLastName())
                .build();
        return customerRepo.save(customerToBeSaved);
    }
    @Transactional
    public Customer updateCustomer(Customer inputCustomer) {
        Customer existingCustomer = customerRepo.findById(inputCustomer.getId()).orElseThrow(() -> new ResourceNotFoundException("Customer not found for customerId: "+ inputCustomer.getId()));
        existingCustomer.setFirstName(inputCustomer.getFirstName());
        existingCustomer.setLastName(inputCustomer.getLastName());
        return customerRepo.save(existingCustomer);
    }
    @Transactional
    public void deleteCustomer(Long customerId) {
        Customer existingCustomer = customerRepo.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found for customerId: "+ customerId));
        customerRepo.delete(existingCustomer);
    }

}
