package com.horizon.customer.rewards.controller;

import com.horizon.customer.rewards.domain.Customer;
import com.horizon.customer.rewards.domain.Transaction;
import com.horizon.customer.rewards.service.RewardSummaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/rewards")
public class RewardSummaryController {

    private final RewardSummaryService rewardSummaryService;

    @GetMapping("/monthlyreport")
    public ResponseEntity<Map<Customer, Integer>> getMonthlyReport(@RequestParam String month) {
        Map<Customer, Integer> monthlyRewardReport = rewardSummaryService.getMonthlyRewardReport(month);
        if(monthlyRewardReport == null || monthlyRewardReport.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(monthlyRewardReport, HttpStatus.OK);
    }
}
