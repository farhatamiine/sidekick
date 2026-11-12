package com.aminef.sidekick.expense.adapter.in.web;

import com.aminef.sidekick.expense.domain.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateExpenseRequest(@NotNull @Positive BigDecimal amount,
                                   @NotBlank @Size(min = 3, max = 3) String currency,
                                   @NotBlank @Size(max = 255) String description,
                                   @NotNull Category category,
                                   @NotNull LocalDate spentOn) {
}
