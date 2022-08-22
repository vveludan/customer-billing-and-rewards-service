package com.charter.customer.rewards;

import com.charter.customer.rewards.domain.Customer;
import com.charter.customer.rewards.domain.Transaction;
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
        Customer customer = Customer.builder().id(1L).build();
        Transaction txn = Transaction.builder().id(1L).billingDate(LocalDate.of(2022, 1, 31)).billingAmount(10.0).customer(customer).build();
        String txnAsString = mapper.writeValueAsString(txn);
        System.out.println(txnAsString);
    }
}
