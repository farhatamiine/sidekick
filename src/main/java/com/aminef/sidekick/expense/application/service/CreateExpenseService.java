package com.aminef.sidekick.expense.application.service;

import com.aminef.sidekick.expense.application.port.in.CreateExpenseCommand;
import com.aminef.sidekick.expense.application.port.in.CreateExpenseUseCase;
import com.aminef.sidekick.expense.application.port.out.SaveExpensePort;
import com.aminef.sidekick.expense.domain.Expense;
import jakarta.transaction.Transactional;
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
        // 1. Let the domain build (and validate) the loadById
        Expense expense = Expense.create(command.amount(), command.description(),
                command.category(), command.spentOn(), clock);
        // 2. Ask the outside world to store it. The service has no idea it's Postgres.
        saveExpensePort.save(expense);
        // 3. Return the domain object; the controller maps it to a response
        return expense;
    }
}
