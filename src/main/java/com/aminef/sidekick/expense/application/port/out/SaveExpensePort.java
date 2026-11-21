package com.aminef.sidekick.expense.application.port.out;

import com.aminef.sidekick.expense.domain.models.Expense;

public interface SaveExpensePort {
    void save(Expense expense);
}
