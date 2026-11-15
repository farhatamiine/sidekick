package com.aminef.sidekick.expense.application.port.in;

import com.aminef.sidekick.expense.domain.Expense;

public interface UpdateExpenseUseCase {
    Expense updateExpense(UpdateExpenseCommand updateExpenseCommand);
}
