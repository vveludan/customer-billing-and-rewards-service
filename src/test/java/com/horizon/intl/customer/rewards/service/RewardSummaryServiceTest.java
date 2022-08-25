package com.horizon.intl.customer.rewards.service;

import com.horizon.intl.customer.rewards.domain.Customer;
import com.horizon.intl.customer.rewards.domain.CustomerRewardPointsReport;
import com.horizon.intl.customer.rewards.domain.Transaction;
import com.horizon.intl.customer.rewards.repos.TransactionRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class RewardSummaryServiceTest {
    @MockBean
    private TransactionRepo transactionRepo;
    private RewardSummaryService rewardSummaryService;

    @Before
    public void setUp() {
        rewardSummaryService = new RewardSummaryService(transactionRepo);
    }


    @Test
    public void givenCustomerAndTransactionForAMonth_WhenGetMonthlyRewardPointsReportIsCalled_ThenReturnExpectedResults() throws Exception {
        Transaction januaryTxnForAllenBorder = createTransactionForMonthlyReportTest();
        // given: Customer[id: 111-11-1111, firstName: Allen, lastName: Border, address: 200 Oak Crest Rd, Lafayette LA 70504]
        // and: Transaction[id: 12345ABC, customer:Customer, billingAmount: 70.0, billingDate: 2022-01-31, rewardPoints: 20]
        given(transactionRepo.findByBillingDate(Mockito.any())).willReturn(Arrays.asList(januaryTxnForAllenBorder));
        // when: rewardSummaryService.getMonthlyRewardPointsReport("january") is called
        List<CustomerRewardPointsReport> monthlyRewardPointsReport = rewardSummaryService.getMonthlyRewardPointsReport("january");
        // then: following assertions should be satisfied
        assertThat(monthlyRewardPointsReport).isNotEmpty();
        CustomerRewardPointsReport customerRewardPointsReport = monthlyRewardPointsReport.get(0);
        assertThat(customerRewardPointsReport.getCustomer()).isEqualTo(createCustomer());
        assertThat(customerRewardPointsReport.getTotalPoints()).isEqualTo(januaryTxnForAllenBorder.getRewardPoints());
        Mockito.verify(transactionRepo, VerificationModeFactory.times(1)).findByBillingDate(Mockito.any());
        Mockito.reset(transactionRepo);
    }

    @Test
    public void givenACustomerAndTranctionsForFirstQuarter_WhenGetQuarterlyRewardPointsReportIsCalled_ThenReturnExpectedResults() throws Exception {
        // given: Customer[id: 111-11-1111, firstName: Allen, lastName: Border, address: 200 Oak Crest Rd, Lafayette LA 70504]
        // and: Transaction for January is: Transaction[id: 111ABC, customer:Customer, billingAmount: 70.0, billingDate: 2022-01-31, rewardPoints: 20]
        // and: Transaction for February is: Transaction[id: 222DEF, customer:Customer, billingAmount: 100.0, billingDate: 2022-02-28, rewardPoints: 50]
        // and: Transaction for March is: Transaction[id: 333GHI, customer:Customer, billingAmount: 120.0, billingDate: 2022-03-31, rewardPoints: 90]
        List<Transaction> quarterlyTransactions = createTransactionsForQuarterlyReportTest();
        given(transactionRepo.findByBillingDateBetween(Mockito.any(), Mockito.any())).willReturn(quarterlyTransactions);
        // when rewardSummaryService.getQuarterlyRewardPointsReport("first) is called with first quarter
        List<CustomerRewardPointsReport> firstQuarterReport = rewardSummaryService.getQuarterlyRewardPointsReport("first");
        // then: following assertions should be satisfied
        assertThat(firstQuarterReport).isNotEmpty();
        CustomerRewardPointsReport reportForAllenBorder = firstQuarterReport.get(0);
        assertThat(reportForAllenBorder.getCustomer()).isEqualTo(createCustomer());
        Integer expectedTotalRewardPoints = quarterlyTransactions.stream().map(txn -> txn.getRewardPoints()).reduce(0, Integer::sum);
        assertThat(reportForAllenBorder.getTotalPoints()).isEqualTo(expectedTotalRewardPoints);
        Mockito.verify(transactionRepo, VerificationModeFactory.times(1)).findByBillingDateBetween(Mockito.any(), Mockito.any());
        Mockito.reset(transactionRepo);

    }

    private Customer createCustomer() {
        return Customer.builder().id("111-11-1111").firstName("Allen").lastName("Border").address("200 Oak Crest Rd, Lafayette LA 70504").build();
    }

    private Transaction createTransactionForMonthlyReportTest() {
        return Transaction.builder()
                .customer(createCustomer())
                .id("12345ABC")
                .billingAmount(70.0)
                .billingDate(LocalDate.of(2022, 01, 31))
                .rewardPoints(20)
                .build();
    }

    private List<Transaction> createTransactionsForQuarterlyReportTest() {
        Customer customer = createCustomer();
        return Arrays.asList(
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
                        .build()
        );
    }


}
