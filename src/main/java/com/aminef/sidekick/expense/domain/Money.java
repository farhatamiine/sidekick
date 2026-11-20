package com.aminef.sidekick.expense.domain;

import com.aminef.sidekick.expense.domain.exception.DomainValidationException;
import com.aminef.sidekick.expense.domain.exception.InvalidCurrencyException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public record Money(BigDecimal amount, Currency currency) {
    public Money{
        if(amount == null || currency == null){
            throw new DomainValidationException("Amount and currency is required");
        }

        amount = amount.setScale(2, RoundingMode.HALF_EVEN);
        if(amount.signum() < 0) throw new DomainValidationException("Amount must be greater than zero");
    }

    public static Money eur(String value){
      return new Money(new BigDecimal(value), Currency.getInstance("EUR"));
    }

    public static Money of(BigDecimal amount, String currencyCode) {
        try {
            return new Money(amount, Currency.getInstance(currencyCode));
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidCurrencyException("Unknown currency: " + currencyCode);
        }
    }
}
