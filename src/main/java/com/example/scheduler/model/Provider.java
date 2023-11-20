package com.example.scheduler.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "PROVIDERS")
@RequiredArgsConstructor
@PrimaryKeyJoinColumn(name = "id_provider")
public class Provider extends User {

//    @OneToMany(mappedBy = "provider")
//    private List<Appointment> appointments;
}
