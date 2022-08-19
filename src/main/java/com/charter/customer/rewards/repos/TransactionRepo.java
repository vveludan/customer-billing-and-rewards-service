package com.charter.customer.rewards.repos;

import com.charter.customer.rewards.domain.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepo extends CrudRepository<Transaction, Long> {

    List<Transaction> findAll();
}
