package com.aminef.sidekick.expense.adapter.in.web;

import com.aminef.sidekick.expense.domain.enums.Category;
import com.aminef.sidekick.expense.domain.models.Expense;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseResponse(UUID id, BigDecimal amount, String currency, String description,
                              Category category, LocalDate spentOn, Instant createdAt) {

    static ExpenseResponse from(Expense e) {
        return new ExpenseResponse(e.getId().value(), e.getAmount().amount(),
                e.getAmount().currency().getCurrencyCode(), e.getDescription(),
                e.getCategory(), e.getSpentOn(), e.getCreatedAt());
    }
}
