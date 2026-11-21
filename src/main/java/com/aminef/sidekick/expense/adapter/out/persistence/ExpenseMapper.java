package com.aminef.sidekick.expense.adapter.out.persistence;

import com.aminef.sidekick.expense.domain.enums.Category;
import com.aminef.sidekick.expense.domain.models.Expense;
import com.aminef.sidekick.expense.domain.models.ExpenseId;
import com.aminef.sidekick.expense.domain.models.Money;

final class ExpenseMapper {
    private ExpenseMapper() {}
    static ExpenseJpaEntity toEntity(Expense e) {
        return new ExpenseJpaEntity(
                e.getId().value(),
                e.getAmount().amount(),
                e.getAmount().currency().getCurrencyCode(),
                e.getDescription(),
                e.getCategory().name(),
                e.getSpentOn(),
                e.getCreatedAt());
    }

    static Expense toDomain(ExpenseJpaEntity j) {
        // rehydrate, not create: loaded data skips the "new loadById" rules
        return Expense.rehydrate(
                new ExpenseId(j.getId()),
                Money.of(j.getAmount(),j.getCurrency()),
                j.getDescription(),
                Category.valueOf(j.getCategory()),
                j.getSpentOn(),
                j.getCreatedAt());
    }
}
