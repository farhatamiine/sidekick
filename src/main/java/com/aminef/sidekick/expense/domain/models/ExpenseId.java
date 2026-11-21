package com.aminef.sidekick.expense.domain.models;

import java.util.Objects;
import java.util.UUID;

public record ExpenseId(UUID value) {
    public ExpenseId {
        Objects.requireNonNull(value,"Id is required");
    }
    public static ExpenseId generate(){
        return new ExpenseId(UUID.randomUUID());
    }
}
