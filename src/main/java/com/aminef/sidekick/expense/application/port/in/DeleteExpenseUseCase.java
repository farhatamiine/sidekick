package com.aminef.sidekick.expense.application.port.in;

import com.aminef.sidekick.expense.domain.models.ExpenseId;

public interface DeleteExpenseUseCase {
    void deleteExpense(ExpenseId id);
}
