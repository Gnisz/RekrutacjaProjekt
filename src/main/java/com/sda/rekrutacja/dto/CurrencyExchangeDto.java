package com.sda.rekrutacja.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Data
public class CurrencyExchangeDto {
        @NotNull
        private String currencyFrom;


        @NotNull
        private String CurrencyTo;


        @Digits(integer = 10, fraction = 2)
        @DecimalMin(value = "0.0", inclusive = false, message = "Value must be higher than 0!")
        private BigDecimal amount;
}
