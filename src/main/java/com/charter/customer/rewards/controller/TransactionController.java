package com.charter.customer.rewards.controller;

import com.charter.customer.rewards.domain.Transaction;
import com.charter.rewards.service.CustomerService;
import com.charter.rewards.service.TransactionService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/rewards")
public class TransactionController {
     private final TransactionService transactionService;

    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return new ResponseEntity<>(transactionService.createTransaction(transaction), HttpStatus.CREATED);
    }
}
