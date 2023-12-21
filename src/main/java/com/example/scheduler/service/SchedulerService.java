package com.example.scheduler.service;

import com.example.scheduler.dto.AppointmentDto;
import com.example.scheduler.model.Appointment;
import com.example.scheduler.model.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SchedulerService {

    public List<Appointment> getAllAppointmentByProvider(String providerId);
    public Appointment getAppointmentById(int id, String providerId);
    public List<Appointment> getAppointmentsByCustomerId(int customerId, String providerId);
    public List<Appointment> getAppointmentsByCustomerIdByDay(int customerId, LocalDate day, String providerId);
    public List<Appointment> getAppointmentsByCustomerIdByStatus(int customerId, AppointmentStatus status, String providerId);
    public void createAppointment(AppointmentDto appointmentDto);
    public void updateAppointment(Appointment appointment);
    public void updateStatus(Appointment appointment, AppointmentStatus status);
}
