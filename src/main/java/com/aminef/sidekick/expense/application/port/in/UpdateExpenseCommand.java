package com.aminef.sidekick.expense.application.port.in;

import com.aminef.sidekick.expense.domain.Category;
import com.aminef.sidekick.expense.domain.ExpenseId;
import com.aminef.sidekick.expense.domain.Money;

import java.time.LocalDate;
import java.util.Objects;

public record UpdateExpenseCommand(ExpenseId expenseId,Money amount, String description,
                                   Category category, LocalDate spentOn) {

    public UpdateExpenseCommand {
        Objects.requireNonNull(amount, "amount is required");
        Objects.requireNonNull(spentOn, "spentOn is required");
    }
}
