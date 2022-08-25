package com.horizon.intl.customer.rewards.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Customer {

    @Id
    @ApiModelProperty(notes = "Customer ID", example = "111-11-1111", required = true)
    private String id;

    @Column(name = "first_name")
    @ApiModelProperty(notes = "Customer First Name", example = "Allen", required = true)
    private String firstName;

    @Column(name = "last_name")
    @ApiModelProperty(notes = "Customer Last Name", example = "Border", required = true)
    private String lastName;

    @Column(name = "address")
    @ApiModelProperty(notes = "Customer Address", example = "200 Oak Crest Rd, Lafayette LA 70504", required = true)
    private String address;
}
