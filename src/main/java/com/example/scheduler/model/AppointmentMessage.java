package com.example.scheduler.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "MESSAGES")
public class AppointmentMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "CREATE_TIME")
    private LocalDateTime createdAt;

    private String message;

//    @ManyToOne
//    private User author;

//    @ManyToOne
//    @JoinColumn(name = "id_appointment")
//    private Appointment appointment;
}
