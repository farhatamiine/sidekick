package com.aminef.sidekick.expense.domain.exception;

public class InvalidCurrencyException extends DomainValidationException {
    public InvalidCurrencyException(String message) {
        super(message);
    }
}
