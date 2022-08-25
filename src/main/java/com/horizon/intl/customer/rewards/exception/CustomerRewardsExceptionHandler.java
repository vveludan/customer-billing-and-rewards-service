package com.horizon.intl.customer.rewards.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class CustomerRewardsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException, WebRequest request) {
        log.error(ExceptionUtils.getStackTrace(resourceNotFoundException));
        return buildErrorResponse(resourceNotFoundException.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException resourceAlreadyExistsException, WebRequest request) {
        log.error(ExceptionUtils.getStackTrace(resourceAlreadyExistsException));
        return buildErrorResponse(resourceAlreadyExistsException.getMessage(), HttpStatus.CONFLICT, request);
    }

    @Override
    public ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return buildErrorResponse(ex.getMessage(), status, request);
    }

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder().status(HttpStatus.UNPROCESSABLE_ENTITY.value()).message("Check for field validation errors.").build();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errorResponse.addValidationErrorMessage(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.unprocessableEntity().body(errorResponse);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleUnknownErrorOrException(Exception exception, WebRequest request) {
        log.error(ExceptionUtils.getStackTrace(exception));
        return buildErrorResponse( exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<Object> buildErrorResponse(String message,
                                                      HttpStatus httpStatus,
                                                      WebRequest request) {
        return ResponseEntity.status(httpStatus).body(ErrorResponse.builder().status(httpStatus.value()).message(message).build());
    }

}
