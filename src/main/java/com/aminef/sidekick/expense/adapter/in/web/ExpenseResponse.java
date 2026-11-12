package com.aminef.sidekick.expense.adapter.in.web;

import com.aminef.sidekick.expense.domain.Category;
import com.aminef.sidekick.expense.domain.Expense;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseResponse(UUID id, BigDecimal amount, String currency, String description,
                              Category category, LocalDate spentOn, Instant createdAt) {

    static ExpenseResponse from(Expense e) {
        return new ExpenseResponse(e.id().value(), e.amount().amount(),
                e.amount().currency().getCurrencyCode(), e.description(),
                e.category(), e.spentOn(), e.createdAt());
    }
}
