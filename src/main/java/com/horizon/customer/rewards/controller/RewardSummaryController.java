package com.horizon.customer.rewards.controller;

import com.horizon.customer.rewards.domain.CustomerRewardPointsReport;
import com.horizon.customer.rewards.service.RewardSummaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
@RequestMapping("/rewards/api/v1")
@ApiOperation("Reward Summary API")
public class RewardSummaryController {

    private final RewardSummaryService rewardSummaryService;

    @GetMapping("/monthlyreport")
    @ApiOperation(value = "Get Monthly Reward Points Report", notes = "Returns CustomerRewardPointsReports")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Retrieved Monthly CustomerRewardPointsReports"),
            @ApiResponse(code = 404, message = "No CustomerRewardPointsReports found for the given month"),
            @ApiResponse(code = 500, message = "Internal Error Occurred While Retrieving Monthly CustomerRewardPointsReports")
    })
    public ResponseEntity<List<CustomerRewardPointsReport>> getMonthlyRewardPointsReport(@RequestParam @ApiParam(name = "month", example = "2022-01-31 (yyyy-MM-dd)") String month) {
        List<CustomerRewardPointsReport> monthlyRewardReports = rewardSummaryService.getMonthlyRewardPointsReport(month);
        if(monthlyRewardReports == null || monthlyRewardReports.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(monthlyRewardReports, HttpStatus.OK);
    }

    @GetMapping("/quarterlyreport")
    @ApiOperation(value = "Get Quarterly Reward Points Report", notes = "Returns CustomerRewardPointsReports")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Retrieved Quarterly CustomerRewardPointsReports"),
            @ApiResponse(code = 404, message = "No CustomerRewardPointsReports found for the given quarter"),
            @ApiResponse(code = 500, message = "Internal Error Occurred While Retrieving Quarterly CustomerRewardPointsReports")
    })
    public ResponseEntity<List<CustomerRewardPointsReport>> getQuarterlyRewardPointsReport(@RequestParam @ApiParam(name = "quarterStartDate", example = "2022-01-31 (yyyy-MM-dd)") String quarterStartDate,
                                                                               @RequestParam @ApiParam(name = "quarterEndDate", example = "2022-03-31 (yyyy-MM-dd)") String quarterEndDate) {
        List<CustomerRewardPointsReport> quarterlyRewardPoints = rewardSummaryService.getQuarterlyRewardPointsReport(quarterStartDate, quarterEndDate);
        if(quarterlyRewardPoints == null || quarterlyRewardPoints.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(quarterlyRewardPoints, HttpStatus.OK);

    }


}
