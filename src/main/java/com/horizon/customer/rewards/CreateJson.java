package com.horizon.customer.rewards;

import com.horizon.customer.rewards.domain.Customer;
import com.horizon.customer.rewards.domain.Transaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;

public class CreateJson {
    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        Customer customer = Customer.builder().id("1").build();
        Transaction txn = Transaction.builder().id("1").billingDate(LocalDate.of(2022, 1, 31)).billingAmount(10.0).customer(customer).build();
        String txnAsString = mapper.writeValueAsString(txn);
        System.out.println(txnAsString);
    }
}
