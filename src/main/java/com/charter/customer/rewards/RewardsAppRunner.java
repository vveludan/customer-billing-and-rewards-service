package com.charter.customer.rewards;

import com.charter.customer.rewards.domain.Customer;
import com.charter.customer.rewards.domain.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.charter.customer.rewards.repos.CustomerRepo;
import com.charter.customer.rewards.repos.TransactionRepo;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Component
public class RewardsAppRunner implements CommandLineRunner {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private TransactionRepo transactionRepo;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        log.info("Customers:");
        List<Customer> customers = (List<Customer>) customerRepo.findAll();
        log.info("Customers Found: "+ customers.size());
        customerRepo.findAll()
                .forEach(c -> log.info(c.toString()));

        log.info("Transactions:");
        List<Transaction> transactions = transactionRepo.findAll();
        log.info("Transactions found: "+ transactions.size());
        transactionRepo.findAll()
                .forEach(t -> log.info(t.toString()));
    }
}
