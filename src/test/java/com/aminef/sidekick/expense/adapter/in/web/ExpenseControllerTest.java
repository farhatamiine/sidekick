package com.aminef.sidekick.expense.adapter.in.web;

import com.aminef.sidekick.expense.application.exception.ExpenseNotFoundException;
import com.aminef.sidekick.expense.application.port.in.CreateExpenseUseCase;
import com.aminef.sidekick.expense.application.port.in.DeleteExpenseUseCase;
import com.aminef.sidekick.expense.application.port.in.GetExpenseUseCase;
import com.aminef.sidekick.expense.application.port.in.UpdateExpenseUseCase;
import com.aminef.sidekick.expense.domain.ExpenseId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ExpenseController.class)
class ExpenseControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockitoBean
    CreateExpenseUseCase createExpenseUseCase;   // controller needs both
    @MockitoBean
    GetExpenseUseCase getExpenseUseCase;
    @MockitoBean
    DeleteExpenseUseCase deleteExpenseUseCase;
    @MockitoBean
    UpdateExpenseUseCase updateExpenseUseCase;

    @Test
    void create() {
    }

    @Test
    void returns404WhenExpenseMissing() throws Exception {
        //generate a random uuid
        UUID expenseId = UUID.randomUUID();
        //run the method get mapping
        when(getExpenseUseCase.getExpense(new ExpenseId(expenseId))).thenThrow(new ExpenseNotFoundException(new ExpenseId(expenseId)));
        mockMvc.perform(get("/api/expenses/{id}", expenseId))
                .andExpect(status().isNotFound());
        //check if it throws 404
    }

    @Test
    void returns400OnInvalidUuid() throws Exception {
        mockMvc.perform(get("/api/expenses/not-a-uuid"))
                .andExpect(status().isBadRequest());
    }

}