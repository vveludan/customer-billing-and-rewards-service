package com.horizon.customer.rewards.service;

import com.horizon.customer.rewards.domain.Customer;
import com.horizon.customer.rewards.domain.Transaction;
import com.horizon.customer.rewards.exception.ResourceAlreadyExistsException;
import com.horizon.customer.rewards.exception.ResourceNotFoundException;
import com.horizon.customer.rewards.repos.CustomerRepo;
import com.horizon.customer.rewards.repos.TransactionRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepo transactionRepo;
    private final CustomerRepo customerRepo;

    @Transactional
    public Transaction createTransaction(Transaction inputTransaction) {
        Customer customer = customerRepo.findById(inputTransaction.getCustomer().getId()).orElseThrow(() -> new ResourceNotFoundException("Customer not found for customerId: "+ inputTransaction.getCustomer().getId()));
        Transaction txnToBeSaved = Transaction.builder()
                .id(UUID.randomUUID().toString())
                .billingAmount(inputTransaction.getBillingAmount())
                .billingDate(inputTransaction.getBillingDate())
                .customer(customer)
                .rewardPoints(computeRewardPoints(inputTransaction.getBillingAmount()))
                .build();
        return transactionRepo.save(txnToBeSaved);
    }

    public Transaction getTransactionById(String txnId) {
        return transactionRepo.findById(txnId).orElseThrow(() -> new ResourceNotFoundException("Transaction not found for id: "+ txnId));
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepo.findAll();
    }
    @Transactional
    public Transaction updateTransaction(Transaction inputTransaction) {
        Transaction existingTxn = transactionRepo.findById(inputTransaction.getId()).orElseThrow(() -> new ResourceNotFoundException("Transaction doesn't exist for transaction id:"+ inputTransaction.getId()));
        existingTxn.setBillingAmount(inputTransaction.getBillingAmount());
        Customer customer = customerRepo.findById(inputTransaction.getCustomer().getId()).orElseThrow(() -> new ResourceNotFoundException("Customer not found for customer id: "+inputTransaction.getCustomer().getId()));
        existingTxn.setCustomer(customer);
        existingTxn.setBillingDate(inputTransaction.getBillingDate());
        existingTxn.setRewardPoints(computeRewardPoints(inputTransaction.getBillingAmount()));
        return transactionRepo.save(existingTxn);
    }
    @Transactional
    public void deleteTransaction(String txnId) {
        Transaction existingTxn = transactionRepo.findById(txnId).orElseThrow(() -> new ResourceNotFoundException("Transaction doesn't exist for transaction id:"+ txnId));
        transactionRepo.delete(existingTxn);
    }

    private Integer computeRewardPoints(Double billingAmount) {
        Integer rewardPoints = 0;
        Integer billingAmountAsInteger = billingAmount.intValue();
        if(billingAmountAsInteger > 50 && billingAmountAsInteger <=100) {
            rewardPoints = billingAmountAsInteger - 50;
        } else if(billingAmountAsInteger > 100) {
            rewardPoints = (billingAmountAsInteger - 50) + (billingAmountAsInteger - 100);
        }
        return rewardPoints;
    }

}
