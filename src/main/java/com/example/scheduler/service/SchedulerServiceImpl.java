package com.example.scheduler.service;

import com.example.scheduler.dto.AppointmentDto;
import com.example.scheduler.exception.AppointmentNotFoundException;
import com.example.scheduler.exception.UserNotFoundException;
import com.example.scheduler.model.*;
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
    public Appointment getAppointmentById(int id) {
        return schedulerRepository.findById(id).orElseThrow(AppointmentNotFoundException::new);
    }

    @Override
    public List<Appointment> getAppointmentsByCustomerId(int userId) {
        return schedulerRepository.findByCustomer_Id(userId);
    }

    @Override
    public List<Appointment> getAppointmentsByCustomerIdByDay(int userId, LocalDate day) {
        return schedulerRepository.findByCustomer_IdAndDay(userId, day);
    }

    @Override
    public List<Appointment> getAppointmentsByCustomerIdByStatus(int userId, AppointmentStatus status) {
        return schedulerRepository.findByCustomer_IdAndStatus(userId, status);
    }

    @Override
    public List<Appointment> getAppointmentsByProviderId(int userId) {
        return schedulerRepository.findByProvider_Id(userId);
    }

    @Override
    public List<Appointment> getAppointmentsByProviderIdByDay(int userId, LocalDate day) {
        return schedulerRepository.findByProvider_IdAndDay(userId, day);
    }

    @Override
    public List<Appointment> getAppointmentsByProviderIdByStatus(int userId, AppointmentStatus status) {
        return schedulerRepository.findByProvider_IdAndStatus(userId, status);
    }

    @Override
    public void createAppointment(AppointmentDto appointmentDto) {
        Appointment appointment = new Appointment();
        User customerUser = userService.findUserById(appointmentDto.getUserId());
        User providerUser = userService.findUserById(appointmentDto.getProviderId());
        Customer customer = createCustomer(customerUser);
        Provider provider = createProvider(providerUser);
        appointment.setCustomer(customer);
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

    private Customer createCustomer(User user) {
        Customer customer = new Customer();
        customer.setId(user.getId());
        customer.setUsername(user.getUsername());
        customer.setFirstName(user.getFirstName());
        customer.setLastName(user.getLastName());
        customer.setEmail(user.getEmail());
        return customer;
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
