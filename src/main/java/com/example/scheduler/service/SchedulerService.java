package com.example.scheduler.service;

import com.example.scheduler.dto.AppointmentDto;
import com.example.scheduler.model.Appointment;
import com.example.scheduler.model.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SchedulerService {

    public Appointment getAppointmentById(int id);
    public List<Appointment> getAppointmentsByCustomerId(int userId);
    public List<Appointment> getAppointmentsByCustomerIdByDay(int userId, LocalDate day);
    public List<Appointment> getAppointmentsByCustomerIdByStatus(int userId, AppointmentStatus status);
    public List<Appointment> getAppointmentsByProviderId(int userId);
    public List<Appointment> getAppointmentsByProviderIdByDay(int userId, LocalDate day);
    public List<Appointment> getAppointmentsByProviderIdByStatus(int userId, AppointmentStatus status);
    public void createAppointment(AppointmentDto appointmentDto);
    public void updateAppointment(Appointment appointment);
}
