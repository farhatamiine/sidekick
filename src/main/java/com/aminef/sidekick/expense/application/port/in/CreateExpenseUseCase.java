package com.aminef.sidekick.expense.application.port.in;

import com.aminef.sidekick.expense.domain.Expense;


// port/in/CreateExpenseUseCase.java
// INBOUND port: what the app offers. One use case per interface, one method.
// The controller depends on this, never on the service class.
public interface CreateExpenseUseCase {
    Expense createExpense(CreateExpenseCommand command);
}
