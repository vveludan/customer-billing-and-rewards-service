package com.horizon.intl.customer.rewards.repos;

import com.horizon.intl.customer.rewards.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, String> {

    List<Transaction> findByBillingDate(LocalDate localDate);
    List<Transaction> findByBillingDateBetween(LocalDate quarterStartDate, LocalDate quarterEndDate);
}
