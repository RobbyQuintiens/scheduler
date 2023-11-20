package com.example.scheduler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimePeriod {

    private LocalTime startTime;
    private LocalTime endTime;
}
