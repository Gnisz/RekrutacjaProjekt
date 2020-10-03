package com.sda.rekrutacja.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TimeService {

    public static LocalDateTime currentTime(){
        LocalDateTime time = LocalDateTime.now();
        return  time;
    }
}
