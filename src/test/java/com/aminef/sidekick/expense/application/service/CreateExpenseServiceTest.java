package com.aminef.sidekick.expense.application.service;

import com.aminef.sidekick.expense.application.port.in.CreateExpenseCommand;
import com.aminef.sidekick.expense.application.port.out.SaveExpensePort;
import com.aminef.sidekick.expense.domain.enums.Category;
import com.aminef.sidekick.expense.domain.models.Expense;
import com.aminef.sidekick.expense.domain.models.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateExpenseServiceTest {
    @Mock
    SaveExpensePort saveExpensePort;
    Clock clock = Clock.fixed(Instant.parse("2026-11-12T10:00:00Z"), ZoneOffset.UTC);

    @Test
    void savesNewExpense() {
        var service = new CreateExpenseService(saveExpensePort, clock);  // built by hand
        var cmd = new CreateExpenseCommand(Money.eur("89.0"), "Navigo",
                Category.TRANSPORT, LocalDate.of(2026, 11, 12));

        Expense result = service.createExpense(cmd);

        verify(saveExpensePort).save(result);   // the service asked the port to save it
    }



}