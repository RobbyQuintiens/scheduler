package com.example.scheduler.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "APPOINTMENTS")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "id_provider")
    private Provider provider;

    @ManyToOne
    @JoinColumn(name = "id_customer")
    private Customer customer;

    private LocalTime startTime;

    private LocalTime endTime;

    @Column(name = "DAY_TIME")
    private LocalDate day;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @OneToMany(mappedBy = "appointment")
    private List<AppointmentMessage> messages;
}
