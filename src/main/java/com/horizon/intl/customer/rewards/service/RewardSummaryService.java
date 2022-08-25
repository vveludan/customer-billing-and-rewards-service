package com.horizon.intl.customer.rewards.service;

import com.horizon.intl.customer.rewards.domain.Customer;
import com.horizon.intl.customer.rewards.domain.CustomerRewardPointsReport;
import com.horizon.intl.customer.rewards.domain.Transaction;
import com.horizon.intl.customer.rewards.repos.TransactionRepo;
import com.horizon.intl.customer.rewards.service.util.Quarter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

@Service
@AllArgsConstructor
@Slf4j
public class RewardSummaryService {
    private final TransactionRepo transactionRepo;

    public List<CustomerRewardPointsReport> getMonthlyRewardPointsReport(String month) {
        if(StringUtils.isBlank(month)) {
            throw new IllegalArgumentException("month cannot be null or empty to get monthly reward points report");
        }
        LocalDate dateForMonthlyReport = getMonthForMonthlyReport(month.toUpperCase());
        List<Transaction> monthlyTxns = transactionRepo.findByBillingDate(dateForMonthlyReport);
        List<CustomerRewardPointsReport> customerRewardPointsReports = monthlyTxns.parallelStream()
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
        if(StringUtils.isBlank(quarter)) {
            throw new IllegalArgumentException("quarter value cannot be null or empty to get quarterly reward points report");
        }
        LocalDate quarterStartDate = getQuarterStartDate(quarter.toLowerCase());
        LocalDate quarterEndDate = getQuarterEndDate(quarterStartDate);
        List<Transaction> quarterlyTxns = transactionRepo.findByBillingDateBetween(quarterStartDate, quarterEndDate);
        Map<Customer, Integer> quarterlyRewardPointsReport = quarterlyTxns.parallelStream()
                .collect(groupingBy(Transaction::getCustomer, summingInt(Transaction::getRewardPoints)));
        List<CustomerRewardPointsReport> customerRewardPointsReports = quarterlyRewardPointsReport.keySet().parallelStream()
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

    private LocalDate getMonthForMonthlyReport(String month) {
        Month monthValue = Month.valueOf(month);
        int currentYear = LocalDate.now().getYear();
        YearMonth yearMonth = YearMonth.of(currentYear, monthValue);
        return yearMonth.atEndOfMonth();
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
