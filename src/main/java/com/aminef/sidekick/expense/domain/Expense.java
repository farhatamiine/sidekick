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


    private static void validate(String description, Category category,LocalDate spentOn, Clock clock,Money amount){
        if (description == null || description.isBlank())
            throw new DomainValidationException("Description is required");
        if(amount == null || amount.amount().signum() <= 0){
            throw new DomainValidationException("Amount is required and should be greater than 0");
        }
        if (category == null)
            throw new DomainValidationException("Category is required");
        if (spentOn == null || spentOn.isAfter(LocalDate.now(clock)))
            throw new DomainValidationException("Expense date can't be in the future");
    }

    public static Expense create(Money amount, String description, Category category,
                                 LocalDate spentOn, Clock clock) {
        validate(description,category,spentOn,clock,amount);
        return new Expense(ExpenseId.generate(), amount, description.trim(),
                category, spentOn, clock.instant());
    }

    public Expense update(Money amount, String description, Category category,
                          LocalDate spentOn, Clock clock){
        validate(description,category,spentOn,clock,amount);
        return new Expense(this.id, amount, description.trim(),
                category, spentOn, this.createdAt);
    }

    public static Expense rehydrate(ExpenseId id, Money amount, String description,
                                    Category category, LocalDate spentOn, Instant createdAt) {
        return new Expense(id, amount, description, category, spentOn, createdAt);
    }
}
