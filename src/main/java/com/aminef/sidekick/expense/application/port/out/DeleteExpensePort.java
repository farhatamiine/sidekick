package com.aminef.sidekick.expense.application.port.out;

import com.aminef.sidekick.expense.domain.Expense;
import com.aminef.sidekick.expense.domain.ExpenseId;

public interface DeleteExpensePort {
    void deleteExpense(ExpenseId expenseId);
}
