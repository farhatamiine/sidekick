package com.aminef.sidekick.expense.domain;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;

public record Expense(ExpenseId id, Money amount, Category category, String description, LocalDate spentOn,
                      Instant createdAt) {
    private Expense(ExpenseId id, Money amount, String description,
                    Category category, LocalDate spentOn, Instant createdAt) {
        this(id, amount, category, description, spentOn, createdAt);
    }


    // Factory #1: a BRAND-NEW expense. Business rules live here.
    public static Expense create(Money amount, String description, Category category,
                                 LocalDate spentOn, Clock clock) {
        if (description == null || description.isBlank())
            throw new DomainValidationException("Description is required");
        if (category == null)
            throw new DomainValidationException("Category is required");
        if (spentOn == null || spentOn.isAfter(LocalDate.now(clock)))
            throw new DomainValidationException("Expense date can't be in the future");
        return new Expense(ExpenseId.generate(), amount, description.trim(),
                category, spentOn, clock.instant());
    }

    // Factory #2: rebuilding an expense LOADED from the DB.
    public static Expense rehydrate(ExpenseId id, Money amount, String description,
                                    Category category, LocalDate spentOn, Instant createdAt) {
        return new Expense(id, amount, description, category, spentOn, createdAt);
    }
}
