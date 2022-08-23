package com.horizon.customer.rewards.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Transaction {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "bill_date")
    private LocalDate billingDate;

    @Column(name = "bill_amt")
    private Double billingAmount;

    @Column(name = "rwrd_pts")
    private Integer rewardPoints;
}
