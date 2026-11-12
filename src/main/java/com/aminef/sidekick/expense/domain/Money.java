package com.aminef.sidekick.expense.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {
    public Money{
        Objects.requireNonNull(amount, "Amount is required");
        Objects.requireNonNull(currency, "Currency is required");
        if(amount.signum() <= 0) throw new DomainValidationException("Amount must be greater than or equal to zero");
        amount = amount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static Money eur(String value){
      return new Money(new BigDecimal(value), Currency.getInstance("EUR"));
    }
}
