package com.aminef.sidekick.expense.adapter.out.persistence;

import com.aminef.sidekick.expense.application.port.out.DeleteExpensePort;
import com.aminef.sidekick.expense.application.port.out.LoadExpensePort;
import com.aminef.sidekick.expense.application.port.out.SaveExpensePort;
import com.aminef.sidekick.expense.domain.Expense;
import com.aminef.sidekick.expense.domain.ExpenseId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ExpensePersistenceAdapter implements SaveExpensePort, LoadExpensePort, DeleteExpensePort {


    private final SpringDataExpenseRepository  springDataExpenseRepository;

    public ExpensePersistenceAdapter(SpringDataExpenseRepository springDataExpenseRepository) {
        this.springDataExpenseRepository = springDataExpenseRepository;
    }

    @Override
    public void save(Expense expense) {
        springDataExpenseRepository.save(ExpenseMapper.toEntity(expense));
    }

    @Override
    public Optional<Expense> loadById(ExpenseId expenseId) {
        return  springDataExpenseRepository.findById(expenseId.value()).map(ExpenseMapper::toDomain);
    }

    @Override
    public List<Expense> loadAllExpenses() {
        return springDataExpenseRepository.findAll().stream().map(ExpenseMapper::toDomain).toList();
    }

    @Override
    public void deleteExpense(ExpenseId expenseId) {
        springDataExpenseRepository.deleteById(expenseId.value());
    }
}
