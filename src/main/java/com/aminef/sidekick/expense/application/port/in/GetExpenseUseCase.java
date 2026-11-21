package com.aminef.sidekick.expense.application.port.in;

import com.aminef.sidekick.expense.domain.models.Expense;
import com.aminef.sidekick.expense.domain.models.ExpenseId;

import java.util.List;

public interface GetExpenseUseCase {
    Expense getExpense(ExpenseId id);
    List<Expense> getExpenses();
}
