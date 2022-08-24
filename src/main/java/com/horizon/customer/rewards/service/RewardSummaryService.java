package com.horizon.customer.rewards.service;

import com.horizon.customer.rewards.domain.Customer;
import com.horizon.customer.rewards.domain.CustomerRewardPointsReport;
import com.horizon.customer.rewards.domain.Transaction;
import com.horizon.customer.rewards.repos.CustomerRepo;
import com.horizon.customer.rewards.repos.TransactionRepo;
import com.horizon.customer.rewards.service.util.Quarter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
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

    public List<CustomerRewardPointsReport> getQuarterlyRewardPointsReport(String quarter) {
        LocalDate quarterStartDate = getQuarterStartDate(quarter);
        LocalDate quarterEndDate = getQuarterEndDate(quarterStartDate);
        List<Transaction> quarterlyTxns = transactionRepo.findByBillingDateBetween(quarterStartDate, quarterEndDate);
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

    private LocalDate getQuarterStartDate(String quarter) {
        int currentYear = LocalDate.now().getYear();
        Month firstMonthOfQuarter = computeFirstMonthOfQuarter(quarter);
        return LocalDate.of(currentYear, firstMonthOfQuarter.getValue(), 01);
    }

    private LocalDate getQuarterEndDate(LocalDate quarterStartDate) {
        return quarterStartDate.plusMonths(2)
                .with(TemporalAdjusters.lastDayOfMonth());
    }

    private Month computeFirstMonthOfQuarter(String quarter) {
        Quarter quarterValue = Quarter.getQuarter(quarter);
        Month month = null;
        switch (quarterValue) {
            case FIRST:
                month = Month.JANUARY;
                break;
            case SECOND:
                month = Month.APRIL;
                break;
            case THIRD:
                month = Month.JULY;
                break;
            case FOURTH:
                month = Month.OCTOBER;
                break;
            default:
                log.error("Invalid quarter found: " + quarter);
                break;
        }
        return month;
    }
}
