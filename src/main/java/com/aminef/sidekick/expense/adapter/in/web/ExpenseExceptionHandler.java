package com.aminef.sidekick.expense.adapter.in.web;

import com.aminef.sidekick.expense.domain.DomainValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExpenseExceptionHandler {

    @ExceptionHandler(DomainValidationException.class)
    ProblemDetail invalid(DomainValidationException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
