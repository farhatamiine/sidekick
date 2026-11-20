package com.aminef.sidekick.expense.domain.exception;

public class DomainValidationException extends RuntimeException {
    public DomainValidationException() {
        super();
    }

    public DomainValidationException(String message) {
        super(message);
    }
}
