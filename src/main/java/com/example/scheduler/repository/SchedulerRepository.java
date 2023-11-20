package com.example.scheduler.repository;

import com.example.scheduler.model.Appointment;
import com.example.scheduler.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SchedulerRepository extends JpaRepository<Appointment, Integer> {

    Optional<Appointment> findById(int id);

    List<Appointment> findByCustomer_Id(int userId);

    List<Appointment> findByProvider_Id(int providerId);

    List<Appointment> findByCustomer_IdAndDay(int userId, LocalDate day);

    List<Appointment> findByProvider_IdAndDay(int providerId, LocalDate day);

    List<Appointment> findByCustomer_IdAndStatus(int userId, AppointmentStatus status);

    List<Appointment> findByProvider_IdAndStatus(int providerId, AppointmentStatus status);
}
