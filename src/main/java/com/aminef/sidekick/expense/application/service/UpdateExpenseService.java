package com.aminef.sidekick.expense.application.service;


import com.aminef.sidekick.expense.application.exception.ExpenseNotFoundException;
import com.aminef.sidekick.expense.application.port.in.UpdateExpenseCommand;
import com.aminef.sidekick.expense.application.port.in.UpdateExpenseUseCase;
import com.aminef.sidekick.expense.application.port.out.LoadExpensePort;
import com.aminef.sidekick.expense.application.port.out.SaveExpensePort;
import com.aminef.sidekick.expense.domain.Expense;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.Optional;

@Service
public class UpdateExpenseService implements UpdateExpenseUseCase {

    private final SaveExpensePort saveExpensePort;
    private final LoadExpensePort loadExpensePort;
    private final Clock clock;


    public UpdateExpenseService(SaveExpensePort saveExpensePort, LoadExpensePort loadExpensePort, Clock clock) {
        this.saveExpensePort = saveExpensePort;
        this.loadExpensePort = loadExpensePort;
        this.clock = clock;
    }

    @Override
    @Transactional
    public Expense updateExpense(UpdateExpenseCommand updateExpenseCommand) {
        Optional<Expense> expenseOptional =  loadExpensePort.loadById(updateExpenseCommand.expenseId());
        if (expenseOptional.isEmpty()) {
            throw new ExpenseNotFoundException(updateExpenseCommand.expenseId());
        }
        Expense expense = expenseOptional.get().update(updateExpenseCommand.amount(),updateExpenseCommand.description(),updateExpenseCommand.category(),updateExpenseCommand.spentOn(),clock);
        saveExpensePort.save(expense);
        return expense;
    }
}
