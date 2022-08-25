package com.horizon.customer.rewards.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horizon.customer.rewards.CustomerRewardsServiceApplication;
import com.horizon.customer.rewards.domain.Customer;
import com.horizon.customer.rewards.domain.CustomerRewardPointsReport;
import com.horizon.customer.rewards.domain.Transaction;
import com.horizon.customer.rewards.repos.CustomerRepo;
import com.horizon.customer.rewards.repos.TransactionRepo;
import com.horizon.customer.rewards.service.RewardSummaryService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




@RunWith(value = SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CustomerRewardsServiceApplication.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
@AutoConfigureTestDatabase
public class RewardSummaryControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private TransactionRepo transactionRepo;


    private RewardSummaryService rewardSummaryService;

    @After
    public void resetDb() {
        transactionRepo.deleteAll();
        customerRepo.deleteAll();
    }

    @Before
    public void setUp() {
        rewardSummaryService = new RewardSummaryService(transactionRepo, customerRepo);
        createCustomerAndTransactions();
    }

    @Test
    public void givenCustomerAndTransactionForAMonth_WhenGetMonthlyRewardPointsReportEndPointIsCalled_ThenReturnExpectedResults() throws Exception {
        mvc.perform(get("/rewards/api/v1/monthlyreport?month=january").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].customer.id", is("111-11-1111")))
                .andExpect(jsonPath("$[0].customer.firstName", is("Allen")))
                .andExpect(jsonPath("$[0].customer.lastName", is("Border")))
                .andExpect(jsonPath("$[0].customer.address", is("200 Oak Crest Rd, Lafayette LA 70504")))
                .andExpect(jsonPath("$[0].totalPoints", is(20)));
    }

    @Test
    public void givenCustomerAndTransactionForAQuarter_WhenGetQuarterlyRewardPointsReportEndPointIsCalled_ThenReturnExpectedResults() throws Exception {
        mvc.perform(get("/rewards/api/v1/quarterlyreport?quarter=first").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].customer.id", is("111-11-1111")))
                .andExpect(jsonPath("$[0].customer.firstName", is("Allen")))
                .andExpect(jsonPath("$[0].customer.lastName", is("Border")))
                .andExpect(jsonPath("$[0].customer.address", is("200 Oak Crest Rd, Lafayette LA 70504")))
                .andExpect(jsonPath("$[0].totalPoints", is(160)));
    }


    private void createCustomerAndTransactions() {
        Customer customer = Customer.builder().id("111-11-1111").firstName("Allen").lastName("Border").address("200 Oak Crest Rd, Lafayette LA 70504").build();
        List<Transaction> txns = Arrays.asList(
                Transaction.builder()
                        .customer(customer)
                        .id("111ABC")
                        .billingAmount(70.0)
                        .billingDate(LocalDate.of(2022, 01, 31))
                        .rewardPoints(20)
                        .build(),
                Transaction.builder()
                        .customer(customer)
                        .id("222DEF")
                        .billingAmount(100.0)
                        .billingDate(LocalDate.of(2022, 02, 28))
                        .rewardPoints(50)
                        .build(),
                Transaction.builder()
                        .customer(customer)
                        .id("333GHI")
                        .billingAmount(120.0)
                        .billingDate(LocalDate.of(2022, 03, 31))
                        .rewardPoints(90)
                        .build());
        customerRepo.save(customer);
        txns.forEach(txn -> {
            transactionRepo.save(txn);
        });
    }

}
