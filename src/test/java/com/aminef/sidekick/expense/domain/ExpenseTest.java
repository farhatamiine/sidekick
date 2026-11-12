package com.aminef.sidekick.expense.domain;

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
}