package com.example.scheduler.service;

import com.example.scheduler.model.*;
import com.example.scheduler.repository.SchedulerRepository;
import com.example.scheduler.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SchedulerServiceTests {

    private final static LocalTime START_TIME = LocalTime.now();

    @InjectMocks
    private SchedulerServiceImpl schedulerService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private SchedulerRepository schedulerRepository;

    @Test
    public void getAppointmentByIdTest() {
        Appointment appointment = createAppointment(1, "appointment title", AppointmentStatus.CONFIRMED, START_TIME);
        when(schedulerRepository.findById(1)).thenReturn(Optional.of(appointment));

        Appointment foundAppointment = schedulerService.getAppointmentById(1);

        assertThat(foundAppointment.getTitle()).isEqualTo("appointment title");
    }

    @Test
    public void getAppointmentsByCustomerIdTest() {
        Appointment appointment = createAppointment(1, "appointment title", AppointmentStatus.CONFIRMED, START_TIME);
        Customer customer = createCustomer(1, "user");
        appointment.setCustomer(customer);

        when(schedulerRepository.findByCustomer_Id(1)).thenReturn(singletonList(appointment));

        List<Appointment> foundAppointments = schedulerService.getAppointmentsByCustomerId(1);

        assertThat(foundAppointments.get(0).getTitle()).isEqualTo("appointment title");
    }

    @Test
    public void getAppointmentsByUserIdByDayTest() {
        Appointment appointment = createAppointment(1, "appointment title", AppointmentStatus.CONFIRMED, START_TIME);
        Customer customer = createCustomer(1, "user");
        appointment.setCustomer(customer);

        when(schedulerRepository.findByCustomer_IdAndDay(1, LocalDate.now()))
                .thenReturn(singletonList(appointment));

        List<Appointment> foundAppointments = schedulerService.getAppointmentsByCustomerIdByDay(customer.getId(),
                appointment.getDay());

        assertThat(foundAppointments.get(0).getTitle()).isEqualTo("appointment title");
    }

    @Test
    public void getAppointmentsByUserIdByStatusTest() {
        Appointment appointment = createAppointment(1, "appointment title", AppointmentStatus.SCHEDULED, START_TIME);
        Customer customer = createCustomer(1, "user");
        appointment.setCustomer(customer);

        when(schedulerRepository.findByCustomer_IdAndStatus(1,
                AppointmentStatus.SCHEDULED)).thenReturn(singletonList(appointment));

        List<Appointment> foundAppointments = schedulerService.getAppointmentsByCustomerIdByStatus(customer.getId(),
                AppointmentStatus.SCHEDULED);

        assertThat(foundAppointments.get(0).getTitle()).isEqualTo("appointment title");
    }

    @Test
    public void getAppointmentsByProviderIdTest() {
        Appointment appointment = createAppointment(1, "appointment title", AppointmentStatus.CONFIRMED, START_TIME);
        Provider provider = createProvider(1, "provider");
        appointment.setProvider(provider);

        when(schedulerRepository.findByProvider_Id(1)).thenReturn(singletonList(appointment));

        List<Appointment> foundAppointments = schedulerService.getAppointmentsByProviderId(provider.getId());

        assertThat(foundAppointments.get(0).getTitle()).isEqualTo("appointment title");
    }

    @Test
    public void getAppointmentsByProviderIdByDayTest() {
        Appointment appointment = createAppointment(1, "appointment title", AppointmentStatus.CONFIRMED, START_TIME);
        Provider provider = createProvider(1, "provider");
        appointment.setProvider(provider);

        when(schedulerRepository.findByProvider_IdAndDay(1, LocalDate.now()))
                .thenReturn(singletonList(appointment));

        List<Appointment> foundAppointments = schedulerService.getAppointmentsByProviderIdByDay(provider.getId(),
                appointment.getDay());

        assertThat(foundAppointments.get(0).getTitle()).isEqualTo("appointment title");
    }

    @Test
    public void getAppointmentsByProviderIdByStatusTest() {
        Appointment appointment = createAppointment(1, "appointment title", AppointmentStatus.SCHEDULED, START_TIME);
        Provider provider = createProvider(1, "provider");
        appointment.setProvider(provider);

        when(schedulerRepository.findByProvider_IdAndStatus(1,
                AppointmentStatus.SCHEDULED)).thenReturn(singletonList(appointment));

        List<Appointment> foundAppointments = schedulerService.getAppointmentsByProviderIdByStatus(provider.getId(),
                AppointmentStatus.SCHEDULED);

        assertThat(foundAppointments.get(0).getTitle()).isEqualTo("appointment title");
    }

    @Test
    public void createAppointmentTest() {

    }

    @Test
    public void updateAppointmentTest() {

    }

    private Appointment createAppointment(int id, String title, AppointmentStatus status, LocalTime start) {
        Appointment appointment = new Appointment();
        appointment.setId(id);
        appointment.setTitle(title);
        appointment.setStatus(status);
        appointment.setStartTime(start);
        appointment.setEndTime(start.plusHours(1));
        appointment.setDay(LocalDate.now());
        return appointment;
    }

    private Customer createCustomer(int id, String username) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setUserId("11");
        customer.setUsername(username);
        customer.setFirstName("firstName");
        customer.setLastName("lastName");
        return customer;
    }

    private Provider createProvider(int id, String username) {
        Provider provider = new Provider();
        provider.setId(id);
        provider.setUserId("12");
        provider.setUsername(username);
        provider.setFirstName("firstName");
        provider.setLastName("lastName");
        return provider;
    }

    private Address createAddress() {
        Address address = new Address();
        address.setId(1);
        address.setCity("City");
        address.setStreet("Street");
        address.setNumber("1b");
        address.setZipCode("3500");
        address.setCountry("Belgium");
        return address;
    }

}
