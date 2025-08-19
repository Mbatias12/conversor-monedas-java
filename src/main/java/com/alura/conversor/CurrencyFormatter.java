package com.alura.conversor;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class CurrencyFormatter {
    private static final Locale DEFAULT_LOCALE = new Locale("es", "CL");

    public static String fmt(BigDecimal amount, String currencyCode) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(DEFAULT_LOCALE);
        nf.setCurrency(Currency.getInstance(currencyCode));
        return nf.format(amount);
    }
}