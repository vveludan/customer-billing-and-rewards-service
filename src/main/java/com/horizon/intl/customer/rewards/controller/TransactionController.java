package com.horizon.intl.customer.rewards.controller;

import com.horizon.intl.customer.rewards.domain.Transaction;
import com.horizon.intl.customer.rewards.service.TransactionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rewards/api/v1")
@ApiOperation("Transactions API")
public class TransactionController {
     private final TransactionService transactionService;

    @PostMapping("/transactions")
    @ApiOperation(value = "Create Transaction", notes = "Returns Created Transaction")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully Created Transaction"),
            @ApiResponse(code = 409, message = "Cannot Create Transaction as Transaction Already Exists"),
            @ApiResponse(code = 500, message = "Internal Error Occurred While Creating Transaction")
    })
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return new ResponseEntity<>(transactionService.createTransaction(transaction), HttpStatus.CREATED);
    }

    @GetMapping("/transactions/{id}")
    @ApiOperation(value = "Get Transaction By Id", notes = "Returns Transaction For Given Transaction Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Retrieved Transaction"),
            @ApiResponse(code = 404, message = "Transaction not found for Transaction id:"),
            @ApiResponse(code = 500, message = "Internal Error Occurred While Retrieving Transaction")
    })
    public ResponseEntity<Transaction> getTransactionById(@PathVariable @ApiParam(name = "id", example = "08147ee6-ca93-4bd7-ac41-4b64a9d69f07") String id) {
        return new ResponseEntity<>(transactionService.getTransactionById(id), HttpStatus.OK);
    }

    @GetMapping("/transactions")
    @ApiOperation(value = " Get All Transactions")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Retrieved All Transactions"),
            @ApiResponse(code = 204, message = "No Transactions Found"),
            @ApiResponse(code = 500, message = "Internal Error Occurred While Retrieving Transactions")
    })
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> allTxns = transactionService.getAllTransactions();
        if(allTxns == null || allTxns.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allTxns, HttpStatus.OK);
    }

    @PutMapping("/transactions")
    @ApiOperation(value = "Update Transaction", notes = "Returns Updated Transaction")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully Updated Transaction"),
            @ApiResponse(code = 404, message = "Cannot Update Transaction as Transaction not found for Transaction Id:"),
            @ApiResponse(code = 500, message = "Internal Error Occurred while Updating Transaction")
    })
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) {
        return new ResponseEntity<>(transactionService.updateTransaction(transaction), HttpStatus.OK);
    }

    @DeleteMapping("/transactions/{id}")
    @ApiOperation(value = "Delete Transaction", notes = "Returns 204 status on Success")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully Deleted Transaction"),
            @ApiResponse(code = 404, message = "Cannot Delete Transaction as Transaction not found for Transaction Id: "),
            @ApiResponse(code = 500, message = "Internal Error Occurred While Deleting Transaction")
    })
    public ResponseEntity<HttpStatus> deleteTransaction(@PathVariable @ApiParam(name = "id", example = "08147ee6-ca93-4bd7-ac41-4b64a9d69f07") String id) {
        transactionService.deleteTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
