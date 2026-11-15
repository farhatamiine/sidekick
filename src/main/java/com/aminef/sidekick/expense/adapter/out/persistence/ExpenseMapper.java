package com.aminef.sidekick.expense.adapter.out.persistence;

import com.aminef.sidekick.expense.domain.Category;
import com.aminef.sidekick.expense.domain.Expense;
import com.aminef.sidekick.expense.domain.ExpenseId;
import com.aminef.sidekick.expense.domain.Money;

import java.util.Currency;

final class ExpenseMapper {
    private ExpenseMapper() {}
    static ExpenseJpaEntity toEntity(Expense e) {
        return new ExpenseJpaEntity(
                e.id().value(),
                e.amount().amount(),
                e.amount().currency().getCurrencyCode(),
                e.description(),
                e.category().name(),
                e.spentOn(),
                e.createdAt());
    }

    static Expense toDomain(ExpenseJpaEntity j) {
        // rehydrate, not create: loaded data skips the "new loadById" rules
        return Expense.rehydrate(
                new ExpenseId(j.getId()),
                new Money(j.getAmount(), Currency.getInstance(j.getCurrency())),
                j.getDescription(),
                Category.valueOf(j.getCategory()),
                j.getSpentOn(),
                j.getCreatedAt());
    }
}
