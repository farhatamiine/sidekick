package com.aminef.sidekick.expense.application.service;

import com.aminef.sidekick.expense.application.port.in.CreateExpenseCommand;
import com.aminef.sidekick.expense.application.port.in.CreateExpenseUseCase;
import com.aminef.sidekick.expense.application.port.out.SaveExpensePort;
import com.aminef.sidekick.expense.domain.models.Expense;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
public class CreateExpenseService implements CreateExpenseUseCase {


    private final SaveExpensePort saveExpensePort;
    private final Clock clock;


    public CreateExpenseService(SaveExpensePort saveExpensePort, Clock clock) {
        this.saveExpensePort = saveExpensePort;
        this.clock = clock;
    }

    @Override
    @Transactional
    public Expense createExpense(CreateExpenseCommand command) {
        Expense expense = Expense.create(command.amount(), command.description(),
                command.category(), command.spentOn(), clock);
        saveExpensePort.save(expense);
        return expense;
    }
}
