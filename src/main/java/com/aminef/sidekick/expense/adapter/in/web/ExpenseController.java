package com.aminef.sidekick.expense.adapter.in.web;

import com.aminef.sidekick.expense.application.port.in.*;
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
    private final UpdateExpenseUseCase updateExpenseUseCase;
    private final DeleteExpenseUseCase deleteExpenseUseCase;

    public ExpenseController(CreateExpenseUseCase createExpenseUseCase, GetExpenseUseCase getExpenseUseCase, UpdateExpenseUseCase updateExpenseUseCase, DeleteExpenseUseCase deleteExpenseUseCase) {
        this.createExpenseUseCase = createExpenseUseCase;
        this.getExpenseUseCase = getExpenseUseCase;
        this.updateExpenseUseCase = updateExpenseUseCase;
        this.deleteExpenseUseCase = deleteExpenseUseCase;
    }

    @PostMapping
    ResponseEntity<ExpenseResponse> create(@Valid @RequestBody ExpenseRequest req) {
        var command = new CreateExpenseCommand(
                Money.of(req.amount(),req.currency()),
                req.description(), req.category(), req.spentOn());
        Expense created = createExpenseUseCase.createExpense(command);
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

    @PutMapping("/{id}")
    ResponseEntity<ExpenseResponse> updateExpense(@PathVariable UUID id, @Valid @RequestBody ExpenseRequest req) {
        var command = new UpdateExpenseCommand(new ExpenseId(id),Money.of(req.amount(),req.currency()),req.description(),req.category(),req.spentOn());
        Expense updated = updateExpenseUseCase.updateExpense(command);
        return ResponseEntity.ok()
                .body(ExpenseResponse.from(updated));
    }


    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteExpense(@PathVariable UUID id) {
        deleteExpenseUseCase.deleteExpense(new ExpenseId(id));
        return ResponseEntity.noContent().build();
    }
}
