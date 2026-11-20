package com.aminef.sidekick.expense.adapter.in.web;

import com.aminef.sidekick.expense.application.exception.ExpenseNotFoundException;
import com.aminef.sidekick.expense.domain.exception.DomainValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ExpenseExceptionHandler {

    @ExceptionHandler(DomainValidationException.class)
    ProblemDetail invalid(DomainValidationException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    ProblemDetail invalid(ExpenseNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ProblemDetail badParam(MethodArgumentTypeMismatchException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Invalid value for '" + ex.getName() + "': " + ex.getValue());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ProblemDetail> validation(MethodArgumentNotValidException ex){
        Map<String,String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error) -> {
            errors.putIfAbsent(error.getField(),error.getDefaultMessage());
        });
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Validation errors");
        problemDetail.setTitle("Bad Request");
        problemDetail.setProperty("errors",errors);
        return ResponseEntity.badRequest().body(problemDetail);

    }
}
