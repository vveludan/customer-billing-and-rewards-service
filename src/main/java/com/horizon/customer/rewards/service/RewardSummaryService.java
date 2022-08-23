package com.horizon.customer.rewards.service;

import com.horizon.customer.rewards.domain.Customer;
import com.horizon.customer.rewards.domain.CustomerRewardPointsReport;
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

    public List<CustomerRewardPointsReport> getMonthlyRewardPointsReport(String month) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(month, dateTimeFormatter);
        List<Transaction> monthlyTxns = transactionRepo.findByBillingDate(localDate);
        List<CustomerRewardPointsReport> customerRewardPointsReports = monthlyTxns.stream()
                .map(txn -> {
                    return CustomerRewardPointsReport.builder()
                            .customer(txn.getCustomer())
                            .totalPoints(txn.getRewardPoints())
                            .build();
                })
                .collect(toList());
        return customerRewardPointsReports;
    }

    public List<CustomerRewardPointsReport> getQuarterlyRewardPointsReport(String quarterStartDate, String quarterEndDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate quarterStartDateValue = LocalDate.parse(quarterStartDate, dateTimeFormatter);
        LocalDate quarterEndDateValue = LocalDate.parse(quarterEndDate, dateTimeFormatter);
        List<Transaction> quarterlyTxns = transactionRepo.findByBillingDateBetween(quarterStartDateValue, quarterEndDateValue);
        Map<Customer, Integer> quarterlyRewardPointsReport = quarterlyTxns.stream()
                .collect(groupingBy(Transaction::getCustomer, summingInt(Transaction::getRewardPoints)));
        List<CustomerRewardPointsReport> customerRewardPointsReports = quarterlyRewardPointsReport.keySet().stream()
                .map(customer -> {
                    return CustomerRewardPointsReport.builder()
                            .customer(customer)
                            .totalPoints(quarterlyRewardPointsReport.get(customer))
                            .build();
                })
                 .collect(toList());
        return customerRewardPointsReports;
    }

}
