package com.horizon.intl.customer.rewards.domain;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "Transaction ID. Not Required by client to set while creating", example = "08147ee6-ca93-4bd7-ac41-4b64a9d69f07", required = true)
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @ApiModelProperty(notes = "Customer object set with id only", example = "Customer.id: 111-11-1111", required = true)
    private Customer customer;

    @Column(name = "bill_date")
    @ApiModelProperty(notes = "Transaction Billing Date", example = "2022-01-31 (yyyy-MM-dd)", required = true)
    private LocalDate billingDate;

    @Column(name = "bill_amt")
    @ApiModelProperty(notes = "Transaction Billing Amount", example = "50.0", required = true)
    private Double billingAmount;

    @Column(name = "rwrd_pts")
    @ApiModelProperty(notes = "Transaction Reward Points Computed, not required to set it while creating/updating", example = "10", required = false)
    private Integer rewardPoints;
}
