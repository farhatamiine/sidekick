package com.aminef.sidekick.expense.application.port.in;

import com.aminef.sidekick.expense.domain.Expense;
import com.aminef.sidekick.expense.domain.ExpenseId;

import java.util.List;
import java.util.Optional;

public interface GetExpenseUseCase {
    Expense getExpense(ExpenseId id);
    List<Expense> getExpenses();
}
