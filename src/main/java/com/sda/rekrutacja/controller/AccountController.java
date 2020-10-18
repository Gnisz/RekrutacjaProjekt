package com.sda.rekrutacja.controller;


import com.sda.rekrutacja.domain.Account;
import com.sda.rekrutacja.dto.CreateAccountDto;
import com.sda.rekrutacja.dto.CurrencyExchangeDto;
import com.sda.rekrutacja.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final RestTemplate restTemplate;
    private final AccountService service;

    @Autowired
    public AccountController(RestTemplate restTemplate, AccountService service) {
        this.restTemplate = restTemplate;
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

    @GetMapping("/test")
    public RateResponse getResponse(){

        RateResponse response = restTemplate.getForObject
                ("http://api.nbp.pl/api/exchangerates/rates/a/usd?format=json", RateResponse.class);

        System.out.println(response);
        return response;
    }

    @GetMapping("/{kwota}/{waluta}")
    public Double getCurrencyExchange(@PathVariable Double kwota,@PathVariable String waluta ){

        String url = "http://api.nbp.pl/api/exchangerates/rates/a/" + waluta +"?format=json";


        RateResponse response = restTemplate.getForObject
                (url, RateResponse.class);

        if(response.getRates() != null & response.getRates().size() ==1){
            double rate = response.getRates().get(0).getMid();

        Double result = kwota/rate;
            System.out.println(result);
            return result;
        }
        return null;
    }

    @GetMapping("/test1")
    public BigDecimal currencyExchange(@Valid CurrencyExchangeDto dto) {
        if (dto.getCurrencyFrom().equalsIgnoreCase("PLN")) {
            String url = "http://api.nbp.pl/api/exchangerates/rates/a/" + dto.getCurrencyTo() +
                    "?format=json";
            RateResponse response = restTemplate.getForObject(url, RateResponse.class);
            BigDecimal rate = BigDecimal.valueOf(response.getRates().get(0).getMid())
                    .setScale(2, RoundingMode.HALF_UP);
            return dto.getAmount().divide(rate, RoundingMode.HALF_DOWN);
        } else {
            String url = "http://api.nbp.pl/api/exchangerates/rates/a/" + dto.getCurrencyFrom() +
                    "?format=json";
            RateResponse response = restTemplate.getForObject(url, RateResponse.class);
            BigDecimal rate = BigDecimal.valueOf(response.getRates().get(0).getMid())
                    .setScale(2, RoundingMode.HALF_UP);
            return dto.getAmount().multiply(rate);
        }
    }



}
