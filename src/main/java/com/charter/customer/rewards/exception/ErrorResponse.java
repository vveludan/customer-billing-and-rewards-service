package com.charter.customer.rewards.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int status;
    private final String message;
    @Builder.Default private List<ValidationErrorMessage> validationErrorMessages = new ArrayList<>();

    public void addValidationErrorMessage(String field, String message){
        validationErrorMessages.add(ValidationErrorMessage.builder().field(field).message(message).build());
    }
}
