package com.example.scheduler.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentDto {

    private int customerId;
    private String providerId;
    private LocalDateTime start;
    private String title;
}
