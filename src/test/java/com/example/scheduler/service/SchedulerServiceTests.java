package com.example.scheduler.service;

import com.example.scheduler.model.Appointment;
import com.example.scheduler.model.AppointmentStatus;
import com.example.scheduler.model.Provider;
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
        when(schedulerRepository.findByIdAndProvider_UserId(1, appointment.getProvider().getUserId())).thenReturn(Optional.of(appointment));

        Appointment foundAppointment = schedulerService.getAppointmentById(1, appointment.getProvider().getUserId());

        assertThat(foundAppointment.getTitle()).isEqualTo("appointment title");
    }

    @Test
    public void getAppointmentsByCustomerIdTest() {
        Appointment appointment = createAppointment(1, "appointment title", AppointmentStatus.CONFIRMED, START_TIME);

        when(schedulerRepository.findByCustomerIdAndProvider_UserId(1, appointment.getProvider().getUserId())).thenReturn(singletonList(appointment));

        List<Appointment> foundAppointments = schedulerService.getAppointmentsByCustomerId(1, appointment.getProvider().getUserId());

        assertThat(foundAppointments.get(0).getTitle()).isEqualTo("appointment title");
    }

    @Test
    public void getAppointmentsByUserIdByDayTest() {
        Appointment appointment = createAppointment(1, "appointment title", AppointmentStatus.CONFIRMED, START_TIME);

        when(schedulerRepository.findByCustomerIdAndDayAndProvider_UserId(1, LocalDate.now(), appointment.getProvider().getUserId()))
                .thenReturn(singletonList(appointment));

        List<Appointment> foundAppointments = schedulerService.getAppointmentsByCustomerIdByDay(appointment.getCustomerId(),
                appointment.getDay(), appointment.getProvider().getUserId());

        assertThat(foundAppointments.get(0).getTitle()).isEqualTo("appointment title");
    }

    @Test
    public void getAppointmentsByUserIdByStatusTest() {
        Appointment appointment = createAppointment(1, "appointment title", AppointmentStatus.SCHEDULED, START_TIME);

        when(schedulerRepository.findByCustomerIdAndStatusAndProvider_UserId(1,
                AppointmentStatus.SCHEDULED, appointment.getProvider().getUserId())).thenReturn(singletonList(appointment));

        List<Appointment> foundAppointments = schedulerService.getAppointmentsByCustomerIdByStatus(appointment.getCustomerId(),
                AppointmentStatus.SCHEDULED, appointment.getProvider().getUserId());

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
        appointment.setCustomerId(1);
        appointment.setProvider(createProvider(1, "provider"));
        return appointment;
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
