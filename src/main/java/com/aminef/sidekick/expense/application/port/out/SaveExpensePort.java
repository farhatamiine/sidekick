package com.aminef.sidekick.expense.application.port.out;

import com.aminef.sidekick.expense.domain.Expense;

// port/out/SaveExpensePort.java
// OUTBOUND port: what the app needs. Takes a DOMAIN object, never a JPA entity.
// The persistence adapter implements this; the service just calls it.
public interface SaveExpensePort {
    void save(Expense expense);
}
