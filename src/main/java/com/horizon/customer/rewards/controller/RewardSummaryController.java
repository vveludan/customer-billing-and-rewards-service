package com.horizon.customer.rewards.controller;

import com.horizon.customer.rewards.domain.CustomerRewardPointsReport;
import com.horizon.customer.rewards.service.RewardSummaryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rewards")
public class RewardSummaryController {

    private final RewardSummaryService rewardSummaryService;

    @GetMapping("/monthlyreport")
    public ResponseEntity<List<CustomerRewardPointsReport>> getMonthlyRewardPointsReport(@RequestParam(name = "month") String month) {
        List<CustomerRewardPointsReport> monthlyRewardReports = rewardSummaryService.getMonthlyRewardPointsReport(month);
        if(monthlyRewardReports == null || monthlyRewardReports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(monthlyRewardReports, HttpStatus.OK);
    }

    @GetMapping("/quarterlyreport")
    public ResponseEntity<List<CustomerRewardPointsReport>> getQuarterlyRewardPointsReport(@RequestParam(name = "quarterStartDate") String quarterStartDate,
                                                                               @RequestParam(name = "quarterEndDate") String quarterEndDate) {
        List<CustomerRewardPointsReport> quarterlyRewardPoints = rewardSummaryService.getQuarterlyRewardPointsReport(quarterStartDate, quarterEndDate);
        if(quarterlyRewardPoints == null || quarterlyRewardPoints.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(quarterlyRewardPoints, HttpStatus.OK);

    }


}
