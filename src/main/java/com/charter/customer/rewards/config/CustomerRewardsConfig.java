package com.charter.customer.rewards.config;

import com.charter.customer.rewards.repos.CustomerRepo;
import com.charter.rewards.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerRewardsConfig {

    @Autowired
    CustomerRepo customerRepo;

    @Bean
    CustomerService customerService() {
        return new CustomerService(customerRepo);
    }

}
