package com.aminef.sidekick.expense.application.port.out;

import com.aminef.sidekick.expense.domain.Expense;
import com.aminef.sidekick.expense.domain.ExpenseId;

import java.util.List;
import java.util.Optional;

public interface LoadExpensePort {
    Optional<Expense> loadById(ExpenseId expenseId);
    List<Expense> loadAllExpenses();
}
