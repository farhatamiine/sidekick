package com.aminef.sidekick.expense.application.port.in;

import com.aminef.sidekick.expense.domain.enums.Category;
import com.aminef.sidekick.expense.domain.models.ExpenseId;
import com.aminef.sidekick.expense.domain.models.Money;

import java.time.LocalDate;
import java.util.Objects;

public record UpdateExpenseCommand(ExpenseId expenseId,Money amount, String description,
                                   Category category, LocalDate spentOn) {

    public UpdateExpenseCommand {
        Objects.requireNonNull(expenseId,"ExpenseId is required");
        Objects.requireNonNull(amount, "amount is required");
        Objects.requireNonNull(spentOn, "spentOn is required");
    }
}
