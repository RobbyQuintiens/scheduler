package com.example.scheduler.service;

import com.example.scheduler.dto.AppointmentDto;
import com.example.scheduler.dto.CustomerDto;
import com.example.scheduler.exception.AppointmentNotFoundException;
import com.example.scheduler.model.Appointment;
import com.example.scheduler.model.AppointmentStatus;
import com.example.scheduler.model.Provider;
import com.example.scheduler.model.User;
import com.example.scheduler.repository.SchedulerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    private final UserService userService;
    private final SchedulerRepository schedulerRepository;

    public SchedulerServiceImpl(UserService userService, SchedulerRepository schedulerRepository) {
        this.userService = userService;
        this.schedulerRepository = schedulerRepository;
    }

    @Override
    public List<Appointment> getAllAppointmentByProvider(String providerId) {
        return schedulerRepository.findAllByProvider_UserId(providerId);
    }

    @Override
    public Appointment getAppointmentById(int id, String providerId) {
        return schedulerRepository.findByIdAndProvider_UserId(id, providerId).orElseThrow(AppointmentNotFoundException::new);
    }

    @Override
    public List<Appointment> getAppointmentsByCustomerId(int customerId, String providerId) {
        return schedulerRepository.findByCustomerIdAndProvider_UserId(customerId, providerId);
    }

    @Override
    public List<Appointment> getAppointmentsByCustomerIdByDay(int customerId, LocalDate day, String providerId) {
        return schedulerRepository.findByCustomerIdAndDayAndProvider_UserId(customerId, day, providerId);
    }

    @Override
    public List<Appointment> getAppointmentsByCustomerIdByStatus(int customerId, AppointmentStatus status, String providerId) {
        return schedulerRepository.findByCustomerIdAndStatusAndProvider_UserId(customerId, status, providerId);
    }

    @Override
    public void createAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        User providerUser = userService.findUserById(appointmentDto.getProviderId());
        Provider provider = createProvider(providerUser);
        appointment.setCustomerId(appointmentDto.getCustomerId());
        appointment.setProvider(provider);
        appointment.setDay(appointmentDto.getStart().toLocalDate());
        appointment.setStartTime(appointmentDto.getStart().toLocalTime());
        appointment.setEndTime(appointmentDto.getStart().toLocalTime().plusHours(1));
        appointment.setTitle(appointmentDto.getTitle());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        schedulerRepository.save(appointment);
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        schedulerRepository.save(appointment);
    }

    @Override
    public void updateStatus(Appointment appointment, AppointmentStatus status) {
        appointment.setStatus(status);
        schedulerRepository.save(appointment);
    }

    private Provider createProvider(User user) {
        Provider provider = new Provider();
        provider.setId(user.getId());
        provider.setUsername(user.getUsername());
        provider.setFirstName(user.getFirstName());
        provider.setLastName(user.getLastName());
        provider.setEmail(user.getEmail());
        return provider;
    }
}
