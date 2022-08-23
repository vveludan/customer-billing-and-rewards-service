package com.horizon.customer.rewards.service;

import com.horizon.customer.rewards.domain.Customer;
import com.horizon.customer.rewards.domain.Transaction;
import com.horizon.customer.rewards.repos.CustomerRepo;
import com.horizon.customer.rewards.repos.TransactionRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
@Slf4j
public class RewardSummaryService {
    private final TransactionRepo transactionRepo;
    private final CustomerRepo customerRepo;

    public Map<Customer, Integer> getMonthlyRewardReport(String month) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(month, dateTimeFormatter);
        log.info("localDate: "+ localDate.toString());
        List<Transaction> monthlyTxns = transactionRepo.findByBillingDate(localDate);
        if(monthlyTxns != null) {
            monthlyTxns.forEach(txn -> log.info("Txn: "+ txn.toString()));
        }
        Map<Customer, Integer> monthlyRewardsReport = monthlyTxns.stream().collect(toMap(Transaction::getCustomer, Transaction::getRewardPoints));
        return monthlyRewardsReport;
    }

}
