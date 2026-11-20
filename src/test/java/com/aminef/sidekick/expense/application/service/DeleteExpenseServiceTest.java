package com.aminef.sidekick.expense.application.service;

import com.aminef.sidekick.expense.application.port.out.DeleteExpensePort;
import com.aminef.sidekick.expense.application.port.out.LoadExpensePort;
import com.aminef.sidekick.expense.domain.Category;
import com.aminef.sidekick.expense.domain.Expense;
import com.aminef.sidekick.expense.domain.ExpenseId;
import com.aminef.sidekick.expense.domain.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DeleteExpenseServiceTest {

    @Mock
    DeleteExpensePort deleteExpensePort;
    @Mock
    LoadExpensePort loadExpensePort;


    //Arrange => Act => Assert
    @Test
    void shouldDeleteExpense(){

        var expense = new Expense(ExpenseId.generate(),Money.eur("89.0"),
                Category.TRANSPORT, "Navigo", LocalDate.of(2026, 11, 12), Instant.now());
        when(loadExpensePort.loadById(expense.id())).thenReturn(Optional.of(expense));

        var service = new  DeleteExpenseService(deleteExpensePort, loadExpensePort);
        service.deleteExpense(expense.id());
        verify(deleteExpensePort).deleteExpense(expense.id());
    }

}