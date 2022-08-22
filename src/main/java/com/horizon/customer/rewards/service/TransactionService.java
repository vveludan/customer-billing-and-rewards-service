package com.horizon.customer.rewards.service;

import com.horizon.customer.rewards.domain.Customer;
import com.horizon.customer.rewards.domain.Transaction;
import com.horizon.customer.rewards.exception.ResourceAlreadyExistsException;
import com.horizon.customer.rewards.exception.ResourceNotFoundException;
import com.horizon.customer.rewards.repos.CustomerRepo;
import com.horizon.customer.rewards.repos.TransactionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepo transactionRepo;
    private final CustomerRepo customerRepo;

    public Transaction createTransaction(Transaction inputTransaction) {
        Optional<Transaction> existingTxn = transactionRepo.findById(inputTransaction.getId());
        if(existingTxn.isPresent()) {
            throw new ResourceAlreadyExistsException("Transaction Already Exists with id: "+ inputTransaction.getId());
        }
        Customer customer = customerRepo.findById(inputTransaction.getCustomer().getId()).orElseThrow(() -> new ResourceNotFoundException("Customer not found for customerId: "+ inputTransaction.getCustomer().getId()));
        Transaction txnToBeSaved = Transaction.builder()
                .id(inputTransaction.getId())
                .billingAmount(inputTransaction.getBillingAmount())
                .billingDate(inputTransaction.getBillingDate())
                .customer(customer)
                .rewardPoints(computeRewardPoints(inputTransaction.getBillingAmount()))
                .build();
        return transactionRepo.save(txnToBeSaved);
    }

    private Integer computeRewardPoints(Double billingAmount) {
        Integer rewardPoints = 0;
        Integer billingAmountAsInteger = Integer.valueOf(String.valueOf(billingAmount));
        if(billingAmountAsInteger > 50 && billingAmountAsInteger <=100) {
            rewardPoints = billingAmountAsInteger - 50;
        } else if(billingAmountAsInteger > 100) {
            rewardPoints = (billingAmountAsInteger - 50) + (billingAmountAsInteger - 100);
        }
        return rewardPoints;
    }

}
