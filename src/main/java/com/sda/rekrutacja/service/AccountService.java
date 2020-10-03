package com.sda.rekrutacja.service;

import com.sda.rekrutacja.domain.Account;
import com.sda.rekrutacja.dto.CreateAccountDto;
import com.sda.rekrutacja.exeptions.AccountAlredyExistsExeption;
import com.sda.rekrutacja.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountService (AccountRepository repository){
        this.repository = repository;
    }

    public Account create(CreateAccountDto dto) {

        boolean exists = repository.existsAccountByPesel(dto.getPesel());
        if(exists) {
            throw new AccountAlredyExistsExeption("Pesel juz istnieje");
        }

        Account account = Account.builder()
                .name((dto.getName()))
                .surname(dto.getSurname())
                .pesel((dto.getPesel()))
                .creationTime(TimeService.currentTime())
                .value(new BigDecimal(0))
                .build();

        repository.save(account);
        return account;
    }

    public List<Account> getAllAccounts(){
        return repository.findAll();

    }

    public Account findAccountByPesel (String pesel){
        return repository.findAccountByPesel(pesel);
    }




}
