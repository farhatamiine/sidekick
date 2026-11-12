package com.aminef.sidekick.expense.adapter.out.persistence;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "expenses")
public class ExpenseJpaEntity {
    @Id
    private UUID id;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
    @Column(nullable = false, length = 3)
    private String currency;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String category;          // enum stored as String
    @Column(name = "spent_on", nullable = false)
    private LocalDate spentOn;
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

}
