package com.aminef.sidekick.expense.adapter.out.persistence;

import com.aminef.sidekick.expense.application.port.out.SaveExpensePort;
import com.aminef.sidekick.expense.domain.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpensePersistenceAdapter implements SaveExpensePort {


    private final SpringDataExpenseRepository  springDataExpenseRepository;

    public ExpensePersistenceAdapter(SpringDataExpenseRepository springDataExpenseRepository) {
        this.springDataExpenseRepository = springDataExpenseRepository;
    }

    @Override
    public void save(Expense expense) {
        springDataExpenseRepository.save(ExpenseMapper.toEntity(expense));
    }
}
