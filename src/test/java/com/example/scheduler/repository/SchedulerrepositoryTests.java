package com.example.scheduler.repository;

import com.example.scheduler.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class SchedulerrepositoryTests {

    @Autowired
    private SchedulerRepository schedulerRepository;

    @Autowired
    private UserRepository userRepository;

    private Appointment appointment;

    @Test
    public void findByIdTest() {
        createAppointment();
        Appointment foundAppointment = schedulerRepository.findById(appointment.getId()).get();

        assertThat(foundAppointment.getTitle()).isEqualTo("Title");
        assertThat(foundAppointment.getCustomer().getUsername()).isEqualTo("user");
        assertThat(foundAppointment.getProvider().getUsername()).isEqualTo("provider");
        assertThat(foundAppointment.getStartTime()).isEqualTo(appointment.getStartTime());
        assertThat(foundAppointment.getEndTime()).isEqualTo(appointment.getEndTime());
        assertThat(foundAppointment.getDay()).isEqualTo(appointment.getDay());
    }

    @Test
    public void findByCustomerIdTest() {
        createAppointment();
        List<Appointment> foundAppointment = schedulerRepository
                .findByCustomer_Id(appointment.getCustomer().getId());

        assertThat(foundAppointment.get(0).getTitle()).isEqualTo("Title");
        assertThat(foundAppointment.get(0).getCustomer().getUsername()).isEqualTo("user");
        assertThat(foundAppointment.get(0).getProvider().getUsername()).isEqualTo("provider");
    }

    @Test
    public void findByCustomerIdAndDayTest() {
        createAppointment();
        List<Appointment> foundAppointment = schedulerRepository
                .findByCustomer_IdAndDay(appointment.getCustomer().getId(), LocalDate.now());

        assertThat(foundAppointment.get(0).getTitle()).isEqualTo("Title");
        assertThat(foundAppointment.get(0).getCustomer().getUsername()).isEqualTo("user");
        assertThat(foundAppointment.get(0).getProvider().getUsername()).isEqualTo("provider");
    }

    @Test
    public void findByCustomerIdAndStatusTest() {
        createAppointment();
        List<Appointment> foundAppointment = schedulerRepository
                .findByCustomer_IdAndStatus(appointment.getCustomer().getId(), AppointmentStatus.SCHEDULED);

        assertThat(foundAppointment.get(0).getTitle()).isEqualTo("Title");
        assertThat(foundAppointment.get(0).getCustomer().getUsername()).isEqualTo("user");
        assertThat(foundAppointment.get(0).getProvider().getUsername()).isEqualTo("provider");
    }

    @Test
    public void findByProviderIdTest() {
        createAppointment();
        List<Appointment> foundAppointment = schedulerRepository
                .findByProvider_Id(appointment.getProvider().getId());

        assertThat(foundAppointment.get(0).getTitle()).isEqualTo("Title");
        assertThat(foundAppointment.get(0).getCustomer().getUsername()).isEqualTo("user");
        assertThat(foundAppointment.get(0).getProvider().getUsername()).isEqualTo("provider");
    }

    @Test
    public void findByProviderIdAndDayTest() {
        createAppointment();
        List<Appointment> foundAppointment = schedulerRepository
                .findByProvider_IdAndDay(appointment.getProvider().getId(), LocalDate.now());

        assertThat(foundAppointment.get(0).getTitle()).isEqualTo("Title");
        assertThat(foundAppointment.get(0).getCustomer().getUsername()).isEqualTo("user");
        assertThat(foundAppointment.get(0).getProvider().getUsername()).isEqualTo("provider");
    }

    @Test
    public void findByProviderIdAndStatusTest() {
        createAppointment();
        List<Appointment> foundAppointment = schedulerRepository
                .findByProvider_IdAndStatus(appointment.getProvider().getId(), AppointmentStatus.SCHEDULED);

        assertThat(foundAppointment.get(0).getTitle()).isEqualTo("Title");
        assertThat(foundAppointment.get(0).getCustomer().getUsername()).isEqualTo("user");
        assertThat(foundAppointment.get(0).getProvider().getUsername()).isEqualTo("provider");
    }

    private void createAppointment() {
        Customer customer = createCustomer(1, "user");
        Provider provider = createProvider(2, "provider");
        userRepository.save(customer);
        userRepository.save(provider);
        appointment = new Appointment();
        appointment.setId(1);
        appointment.setTitle("Title");
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setStartTime(LocalTime.now());
        appointment.setEndTime(LocalTime.now().plusHours(1));
        appointment.setCustomer(customer);
        appointment.setProvider(provider);
        appointment.setDay(LocalDate.now());
        schedulerRepository.save(appointment);
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
}
