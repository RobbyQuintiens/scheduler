package com.example.scheduler.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "CUSTOMERS")
@RequiredArgsConstructor
@PrimaryKeyJoinColumn(name = "id_customer")
public class Customer extends User {

//    @OneToMany(mappedBy = "customer")
//    private List<Appointment> appointments;
}
