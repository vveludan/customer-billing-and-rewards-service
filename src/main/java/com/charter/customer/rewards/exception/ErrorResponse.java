package com.charter.customer.rewards.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private final int status;
    private final String message;
    private List<ValidationErrorMessage> validationErrorMessages;

    public void addValidationErrorMessage(String field, String message){
        if(Objects.isNull(validationErrorMessages)){
            validationErrorMessages = new ArrayList<>();
        }
        validationErrorMessages.add(ValidationErrorMessage.builder().field(field).message(message).build());
    }
}
