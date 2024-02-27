package com.parkyounbae.challengehere.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class DailyUpdateService {

    private String currentDateString;

    public DailyUpdateService() {
        updateCurrentDateString(); // 서비스 생성 시 현재 날짜로 초기화
    }

    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행 (0 0 0 * * ?)
    public void updateCurrentDateString() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        currentDateString = currentDate.format(formatter);
    }

    public String getCurrentDateString() {
        return currentDateString;
    }
}
