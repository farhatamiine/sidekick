package com.aminef.sidekick.expense.adapter.in.web;

import com.aminef.sidekick.expense.application.port.in.CreateExpenseCommand;
import com.aminef.sidekick.expense.application.port.in.CreateExpenseUseCase;
import com.aminef.sidekick.expense.application.port.in.GetExpenseUseCase;
import com.aminef.sidekick.expense.domain.Expense;
import com.aminef.sidekick.expense.domain.ExpenseId;
import com.aminef.sidekick.expense.domain.Money;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    // the PORT, not the service

    private final CreateExpenseUseCase createExpenseUseCase;
    private final GetExpenseUseCase getExpenseUseCase;

    public ExpenseController(CreateExpenseUseCase createExpenseUseCase, GetExpenseUseCase getExpenseUseCase) {
        this.createExpenseUseCase = createExpenseUseCase;
        this.getExpenseUseCase = getExpenseUseCase;
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

    @GetMapping
    ResponseEntity<List<ExpenseResponse>> findAll() {
        List<Expense> expense = getExpenseUseCase.getExpenses();
        List<ExpenseResponse> expenseResponses = expense.stream().map(ExpenseResponse::from).toList();
        return ResponseEntity.ok(expenseResponses);
    }

    @GetMapping("/{id}")
    ResponseEntity<ExpenseResponse> findById(@PathVariable UUID id) {
        Expense expense = getExpenseUseCase.getExpense(new ExpenseId(id));
        return ResponseEntity.ok(ExpenseResponse.from(expense));
    }
}
