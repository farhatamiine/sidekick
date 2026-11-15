package com.aminef.sidekick.expense.application.service;

import com.aminef.sidekick.expense.application.exception.ExpenseNotFoundException;
import com.aminef.sidekick.expense.application.port.in.DeleteExpenseUseCase;
import com.aminef.sidekick.expense.application.port.out.DeleteExpensePort;
import com.aminef.sidekick.expense.application.port.out.LoadExpensePort;
import com.aminef.sidekick.expense.application.port.out.SaveExpensePort;
import com.aminef.sidekick.expense.domain.ExpenseId;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
public class DeleteExpenseService implements DeleteExpenseUseCase {
    private final DeleteExpensePort deleteExpensePort;
    private final LoadExpensePort loadExpensePort;

    public DeleteExpenseService(DeleteExpensePort deleteExpensePort, LoadExpensePort loadExpensePort) {
        this.deleteExpensePort = deleteExpensePort;
        this.loadExpensePort = loadExpensePort;
    }


    @Override
    public void deleteExpense(ExpenseId id) {
        var expense = loadExpensePort.loadById(id);
        if(expense.isEmpty()){
            throw new ExpenseNotFoundException(id);
        }
        deleteExpensePort.deleteExpense(id);
    }
}
