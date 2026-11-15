package com.aminef.sidekick.expense.application.service;

import com.aminef.sidekick.expense.application.exception.ExpenseNotFoundException;
import com.aminef.sidekick.expense.application.port.in.GetExpenseUseCase;
import com.aminef.sidekick.expense.application.port.out.LoadExpensePort;
import com.aminef.sidekick.expense.domain.Expense;
import com.aminef.sidekick.expense.domain.ExpenseId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetExpenseService implements GetExpenseUseCase {


    private  final LoadExpensePort loadExpensePort;

    public GetExpenseService(LoadExpensePort loadExpensePort) {
        this.loadExpensePort = loadExpensePort;
    }

    @Override
    @Transactional()
    public Expense getExpense(ExpenseId expenseId) {
        return loadExpensePort.loadById(expenseId).orElseThrow(() -> new ExpenseNotFoundException(expenseId));
    }

    @Override
    public List<Expense> getExpenses() {
        return loadExpensePort.loadAllExpenses();
    }
}
