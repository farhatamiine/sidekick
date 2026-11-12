package com.aminef.sidekick.expense.adapter.in.web;

import com.aminef.sidekick.expense.application.port.in.CreateExpenseCommand;
import com.aminef.sidekick.expense.application.port.in.CreateExpenseUseCase;
import com.aminef.sidekick.expense.domain.Expense;
import com.aminef.sidekick.expense.domain.Money;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Currency;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    // the PORT, not the service

    private final CreateExpenseUseCase createExpenseUseCase;

    public ExpenseController(CreateExpenseUseCase createExpenseUseCase) {
        this.createExpenseUseCase = createExpenseUseCase;
    }

    @PostMapping
    ResponseEntity<ExpenseResponse> create(@Valid @RequestBody CreateExpenseRequest req) {
        // 1. HTTP language -> domain language
        var command = new CreateExpenseCommand(
                new Money(req.amount(), Currency.getInstance(req.currency())),
                req.description(), req.category(), req.spentOn());
        // 2. Call the use case
        Expense created = createExpenseUseCase.createExpense(command);
        // 3. Domain -> response, 201 + Location header
        return ResponseEntity
                .created(URI.create("/api/expenses/" + created.id().value()))
                .body(ExpenseResponse.from(created));
    }
}
