package com.aminef.sidekick.expense.application.port.out;

import com.aminef.sidekick.expense.domain.models.ExpenseId;

public interface DeleteExpensePort {
    void deleteExpense(ExpenseId expenseId);
}
