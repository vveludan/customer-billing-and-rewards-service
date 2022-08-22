package com.horizon.customer.rewards.service;

import com.horizon.customer.rewards.repos.CustomerRepo;
import com.horizon.customer.rewards.repos.TransactionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RewardSummaryService {
    private final TransactionRepo transactionRepo;
    private final CustomerRepo customerRepo;

}
