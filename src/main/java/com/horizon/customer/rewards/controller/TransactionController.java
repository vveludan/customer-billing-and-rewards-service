package com.horizon.customer.rewards.controller;

import com.horizon.customer.rewards.domain.Transaction;
import com.horizon.customer.rewards.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rewards")
public class TransactionController {
     private final TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return new ResponseEntity<>(transactionService.createTransaction(transaction), HttpStatus.CREATED);
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return new ResponseEntity<>(transactionService.getTransactionById(id), HttpStatus.OK);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> allTxns = transactionService.getAllTransactions();
        if(allTxns == null || allTxns.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allTxns, HttpStatus.OK);
    }

    @PutMapping("/transactions")
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) {
        return new ResponseEntity<>(transactionService.updateTransaction(transaction), HttpStatus.OK);
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<HttpStatus> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
