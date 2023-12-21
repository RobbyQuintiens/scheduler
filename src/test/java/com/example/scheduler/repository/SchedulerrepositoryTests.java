package com.example.scheduler.repository;

import com.example.scheduler.model.Appointment;
import com.example.scheduler.model.AppointmentStatus;
import com.example.scheduler.model.Provider;
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
        Appointment foundAppointment = schedulerRepository.findByIdAndProvider_UserId(appointment.getId(),
                appointment.getProvider().getUserId()).get();

        assertThat(foundAppointment.getTitle()).isEqualTo("Title");
        assertThat(foundAppointment.getProvider().getUsername()).isEqualTo("provider");
        assertThat(foundAppointment.getStartTime()).isEqualTo(appointment.getStartTime());
        assertThat(foundAppointment.getEndTime()).isEqualTo(appointment.getEndTime());
        assertThat(foundAppointment.getDay()).isEqualTo(appointment.getDay());
    }

    @Test
    public void findByCustomerIdTest() {
        createAppointment();
        List<Appointment> foundAppointment = schedulerRepository
                .findByCustomerIdAndProvider_UserId(appointment.getCustomerId(),
                        appointment.getProvider().getUserId());

        assertThat(foundAppointment.get(0).getTitle()).isEqualTo("Title");
        assertThat(foundAppointment.get(0).getProvider().getUsername()).isEqualTo("provider");
    }

    @Test
    public void findByCustomerIdAndDayTest() {
        createAppointment();
        List<Appointment> foundAppointment = schedulerRepository
                .findByCustomerIdAndDayAndProvider_UserId(appointment.getCustomerId(), LocalDate.now(),
                        appointment.getProvider().getUserId());

        assertThat(foundAppointment.get(0).getTitle()).isEqualTo("Title");
        assertThat(foundAppointment.get(0).getProvider().getUsername()).isEqualTo("provider");
    }

    @Test
    public void findByCustomerIdAndStatusTest() {
        createAppointment();
        List<Appointment> foundAppointment = schedulerRepository
                .findByCustomerIdAndStatusAndProvider_UserId(appointment.getCustomerId(), AppointmentStatus.SCHEDULED,
                        appointment.getProvider().getUserId());

        assertThat(foundAppointment.get(0).getTitle()).isEqualTo("Title");
        assertThat(foundAppointment.get(0).getProvider().getUsername()).isEqualTo("provider");
    }

    @Test
    public void findAllByProviderId() {
        createAppointment();
        List<Appointment> foundAppointment = schedulerRepository
                .findAllByProvider_UserId(appointment.getProvider().getUserId());

        assertThat(foundAppointment.get(0).getTitle()).isEqualTo("Title");
        assertThat(foundAppointment.get(0).getProvider().getUsername()).isEqualTo("provider");
    }

    private void createAppointment() {
        Provider provider = createProvider(2, "provider");
        userRepository.save(provider);
        appointment = new Appointment();
        appointment.setId(1);
        appointment.setTitle("Title");
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setStartTime(LocalTime.now());
        appointment.setEndTime(LocalTime.now().plusHours(1));
        appointment.setCustomerId(1);
        appointment.setProvider(provider);
        appointment.setDay(LocalDate.now());
        schedulerRepository.save(appointment);
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
