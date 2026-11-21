package com.aminef.sidekick.expense.application.exception;

import com.aminef.sidekick.expense.domain.models.ExpenseId;
import lombok.Getter;

@Getter
public class ExpenseNotFoundException extends RuntimeException {

    private final ExpenseId id;

    public ExpenseNotFoundException(ExpenseId id) {
        super("Expense not found: " + id.value());
        this.id = id;
    }

}
