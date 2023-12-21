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

    List<Appointment> findAllByProvider_UserId(String providerId);

    Optional<Appointment> findByIdAndProvider_UserId(int id, String providerId);

    List<Appointment> findByCustomerIdAndProvider_UserId(int userId, String providerId);

    List<Appointment> findByCustomerIdAndDayAndProvider_UserId(int userId, LocalDate day, String providerId);

    List<Appointment> findByCustomerIdAndStatusAndProvider_UserId(int userId, AppointmentStatus status, String providerId);
}
