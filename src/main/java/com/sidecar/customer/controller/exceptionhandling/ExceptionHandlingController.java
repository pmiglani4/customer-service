package com.sidecar.customer.controller.exceptionhandling;

import com.sidecar.customer.dto.response.ErrorDto;
import com.sidecar.customer.exceptions.CustomerNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.OffsetDateTime;

@ControllerAdvice
public class ExceptionHandlingController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @ExceptionHandler
    public ResponseEntity<ErrorDto>  handleAccessDeniedException(AccessDeniedException ex) {
        logger.error("AccessDeniedException occurred ",ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorDto.builder()
                        .status(HttpStatus.FORBIDDEN)
                        .errorCode("ACCESS_DENIED")
                        .errorMessage("Operation Not allowed for this user\"")
                        .time(OffsetDateTime.now())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleDataAccessException(DataAccessException ex) {
        logger.error("Exception: DATA_ACCESS_EXCEPTION " ,ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDto.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .errorCode("DATA_ACCESS_EXCEPTION")
                        .errorMessage("Exception while accessing Database")
                        .time(OffsetDateTime.now())
                        .build());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        logger.error("Exception: DATA_ACCESS_EXCEPTION " ,ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .errorCode("CUSTOMER_NOT_FOUND")
                .errorMessage(ex.getMessage())
                .time(OffsetDateTime.now())
                .build());
    }
}
