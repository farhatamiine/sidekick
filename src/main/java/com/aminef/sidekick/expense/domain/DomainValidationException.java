package com.aminef.sidekick.expense.domain;

public class DomainValidationException extends RuntimeException {
    public DomainValidationException() {
        super();
    }

    public DomainValidationException(String message) {
        super(message);
    }
}
