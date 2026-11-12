package com.aminef.sidekick.expense.application.port.in;

import com.aminef.sidekick.expense.domain.Category;
import com.aminef.sidekick.expense.domain.Money;

import java.time.LocalDate;
import java.util.Objects;


// port/in/CreateExpenseCommand.java
// The input of the use case. Already speaks domain language (Money, Category),
// so the service never sees raw JSON strings.
public record CreateExpenseCommand(Money amount, String description,
                                   Category category, LocalDate spentOn) {

    public CreateExpenseCommand {
        // Cheap null checks here; real business rules stay in Expense.create
        Objects.requireNonNull(amount, "amount is required");
        Objects.requireNonNull(spentOn, "spentOn is required");
    }
}
