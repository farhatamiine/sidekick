package com.aminef.sidekick.expense.application.service;

import com.aminef.sidekick.expense.application.exception.ExpenseNotFoundException;
import com.aminef.sidekick.expense.application.port.in.GetExpenseUseCase;
import com.aminef.sidekick.expense.application.port.out.LoadExpensePort;
import com.aminef.sidekick.expense.domain.Category;
import com.aminef.sidekick.expense.domain.Expense;
import com.aminef.sidekick.expense.domain.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetExpenseServiceTest {

    @Mock
    LoadExpensePort loadExpensePort;
    @InjectMocks
    GetExpenseService service;
    Clock clock = Clock.fixed(Instant.parse("2026-11-12T10:00:00Z"), ZoneOffset.UTC);


    Expense lunch = Expense.create(Money.eur("12.50"), "Lunch", Category.FOOD,
            LocalDate.of(2026, 11, 12), clock);
    @Test
    void returnExpenseWhenFound() {
        when(loadExpensePort.loadById(lunch.id())).thenReturn(Optional.of(lunch));
        assertEquals(lunch, service.getExpense(lunch.id()));
    }

    @Test
    void throwWhenNotFound() {
        when(loadExpensePort.loadById(lunch.id())).thenReturn(Optional.empty());
        assertThrows(ExpenseNotFoundException.class,() -> service.getExpense(lunch.id()));
    }

    @Test
    void returnAllExpenses(){
        when(loadExpensePort.loadAllExpenses()).thenReturn(List.of(lunch));
        assertEquals(1, service.getExpenses().size());
    }

}