package com.horizon.customer.rewards.config;

import com.horizon.customer.rewards.repos.CustomerRepo;
import com.horizon.customer.rewards.repos.TransactionRepo;
import com.horizon.customer.rewards.service.CustomerService;
import com.horizon.customer.rewards.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerRewardsConfig {

 /*   @Autowired
    CustomerRepo customerRepo;

    @Autowired
    TransactionRepo transactionRepo;

    @Bean
    CustomerService customerService() {
        return new CustomerService(customerRepo);
    }

    @Bean
    TransactionService transactionService() {
        return new TransactionService(transactionRepo, customerRepo);
    }*/

}
