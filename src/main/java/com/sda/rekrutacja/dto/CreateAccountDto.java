package com.sda.rekrutacja.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.validator.constraints.pl.PESEL;

@Data
public class CreateAccountDto {

    @NotNull
    private String name;
    @NotNull
    private String surname;

    @NotNull
    @PESEL
    private String pesel;
}
