package com.horizon.customer.rewards.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ValidationErrorMessage {
    private final String field;
    private final String message;

}
