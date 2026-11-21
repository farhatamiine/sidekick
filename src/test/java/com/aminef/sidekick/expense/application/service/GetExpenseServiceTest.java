package com.aminef.sidekick.expense.application.service;

import com.aminef.sidekick.expense.application.exception.ExpenseNotFoundException;
import com.aminef.sidekick.expense.application.port.out.LoadExpensePort;
import com.aminef.sidekick.expense.domain.enums.Category;
import com.aminef.sidekick.expense.domain.models.Expense;
import com.aminef.sidekick.expense.domain.models.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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
        when(loadExpensePort.loadById(lunch.getId())).thenReturn(Optional.of(lunch));
        assertEquals(lunch, service.getExpense(lunch.getId()));
    }

    @Test
    void throwWhenNotFound() {
        when(loadExpensePort.loadById(lunch.getId())).thenReturn(Optional.empty());
        assertThrows(ExpenseNotFoundException.class,() -> service.getExpense(lunch.getId()));
    }

    @Test
    void returnAllExpenses(){
        when(loadExpensePort.loadAllExpenses()).thenReturn(List.of(lunch));
        assertEquals(1, service.getExpenses().size());
    }

}