package com.sda.rekrutacja.controller;


import com.sda.rekrutacja.domain.Account;
import com.sda.rekrutacja.dto.CreateAccountDto;
import com.sda.rekrutacja.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService service;

    @Autowired
    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(
            @RequestBody @Valid CreateAccountDto dto) {

        Account p = service.create(dto);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(){
        List<Account> accounts = service.getAllAccounts();

        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{pesel}")
    public ResponseEntity<Account> findAccountByPesel (@PathVariable String pesel){
        Account account = service.findAccountByPesel(pesel);

        return  ResponseEntity.ok(account);
    }

}
