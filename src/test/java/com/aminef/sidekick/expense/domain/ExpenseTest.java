package com.aminef.sidekick.expense.domain;

import com.aminef.sidekick.expense.domain.enums.Category;
import com.aminef.sidekick.expense.domain.exception.DomainValidationException;
import com.aminef.sidekick.expense.domain.models.Expense;
import com.aminef.sidekick.expense.domain.models.Money;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;


class ExpenseTest {
    Clock  clock = Clock.fixed(Instant.parse("2026-11-12T10:00:00Z"), ZoneOffset.UTC);

    @Test
    void rejectsFutureDate() {
        assertThrows(DomainValidationException.class, () ->
                Expense.create(Money.eur("12.50"), "Lunch", Category.FOOD,
                        LocalDate.of(2026, 11, 13), clock));
    }

    @Test
    void rejectsNonPositiveAmount() {
        assertThrows(DomainValidationException.class, () -> Money.eur("0"));
    }

    @Test
    void createValidExpense(){
        Expense expense = Expense.create(Money.eur("12.50"), " Lunch ", Category.FOOD,
                LocalDate.of(2026, 11, 12), clock);
        assertEquals(expense.getCreatedAt(),clock.instant());
        assertNotNull(expense.getId());
        assertEquals("Lunch", expense.getDescription());
    }

    @Test
    void rejectBlankDescription(){
        assertThrows(DomainValidationException.class,() -> Expense.create(Money.eur("12.50"), "", Category.FOOD,
                LocalDate.of(2026, 11, 12), clock));
    }

    @Test
    void rejectsNullCategory(){
        assertThrows(DomainValidationException.class,() -> Expense.create(Money.eur("12.50"), "Lunch",null,
                LocalDate.of(2026, 11, 12), clock));
    }

    @Test
    void rejectsNullSpentOn(){
        assertThrows(DomainValidationException.class,() -> Expense.create(Money.eur("12.50"), "Lunch",Category.FOOD,
                null, clock));
    }

    @Test
    void updateKeepsIdAndCreatedAt(){
        Expense expense = Expense.create(Money.eur("12.50"), " Lunch ", Category.FOOD,
                LocalDate.of(2026, 11, 12), clock);
        Expense updatedExpense = expense.update(Money.eur("7"),"OVH CLOUD", Category.DEV_TOOLS,LocalDate.of(2026, 11, 12),clock);
        assertEquals(expense.getCreatedAt(),updatedExpense.getCreatedAt());
        assertEquals(expense.getId(),updatedExpense.getId());
    }

    @Test
    void updateValidatesToo(){
        Expense expense = Expense.create(Money.eur("12.50"), " Lunch ", Category.FOOD,
                LocalDate.of(2026, 11, 12), clock);
        assertThrows(DomainValidationException.class,() ->expense.update(Money.eur("7"),"OVH CLOUD", Category.DEV_TOOLS,LocalDate.of(2026, 12, 12),clock));
    }
}