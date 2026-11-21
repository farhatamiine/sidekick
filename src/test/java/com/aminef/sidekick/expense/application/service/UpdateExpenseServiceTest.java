package com.aminef.sidekick.expense.application.service;

import com.aminef.sidekick.expense.application.exception.ExpenseNotFoundException;
import com.aminef.sidekick.expense.application.port.in.UpdateExpenseCommand;
import com.aminef.sidekick.expense.application.port.out.LoadExpensePort;
import com.aminef.sidekick.expense.application.port.out.SaveExpensePort;
import com.aminef.sidekick.expense.domain.enums.Category;
import com.aminef.sidekick.expense.domain.exception.DomainValidationException;
import com.aminef.sidekick.expense.domain.models.Expense;
import com.aminef.sidekick.expense.domain.models.ExpenseId;
import com.aminef.sidekick.expense.domain.models.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateExpenseServiceTest {

    @Mock
    SaveExpensePort saveExpensePort;
    @Mock
    LoadExpensePort loadExpensePort;

    Clock clock = Clock.fixed(Instant.parse("2026-11-12T10:00:00Z"), ZoneOffset.UTC);
    ExpenseId expenseId = ExpenseId.generate();
    Expense expense = new Expense(expenseId, Money.eur("89.0"), Category.FOOD,"Navigo", LocalDate.of(2026, 11, 12),Instant.now());


    @Test
    void updatesExistingExpense(){
        var service = new UpdateExpenseService(saveExpensePort,loadExpensePort,clock);
        var updateExpenseCommand = new UpdateExpenseCommand(expenseId,Money.eur("29"),"Food",Category.FOOD,LocalDate.of(2026,11,11));
        when(loadExpensePort.loadById(expenseId)).thenReturn(Optional.of(expense));
        Expense result = service.updateExpense(updateExpenseCommand);
        verify(loadExpensePort).loadById(expenseId);
        verify(saveExpensePort).save(result);
        assertEquals(expenseId.value(),result.getId().value());
    }

    @Test
    void throwsWhenNotFound(){
        var service = new UpdateExpenseService(saveExpensePort,loadExpensePort,clock);
        var updateExpenseCommand = new UpdateExpenseCommand(expenseId,Money.eur("29"),"Food",Category.FOOD,LocalDate.of(2026,11,11));
        when(loadExpensePort.loadById(any())).thenReturn(Optional.empty());
        assertThrows(ExpenseNotFoundException.class,() -> service.updateExpense(updateExpenseCommand));
        verify(saveExpensePort,never()).save(any());
    }

    @Test
    void doesNotSaveInvalidUpdate(){
        var service = new UpdateExpenseService(saveExpensePort,loadExpensePort,clock);
        var updateExpenseCommand = new UpdateExpenseCommand(expenseId,Money.eur("29"),"Food",Category.FOOD,LocalDate.of(2026,11,25));
        when(loadExpensePort.loadById(expenseId)).thenReturn(Optional.of(expense));
        assertThrows(DomainValidationException.class,() -> service.updateExpense(updateExpenseCommand));
        verify(saveExpensePort, never()).save(any());
    }

}