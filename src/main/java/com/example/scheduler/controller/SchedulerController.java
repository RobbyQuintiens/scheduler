package com.example.scheduler.controller;

import com.example.scheduler.dto.AppointmentDto;
import com.example.scheduler.filter.UserDetailsFilter;
import com.example.scheduler.model.Appointment;
import com.example.scheduler.service.SchedulerService;
import com.example.scheduler.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class SchedulerController {

    private final SchedulerService schedulerService;
    private final UserService userService;

    public SchedulerController(SchedulerService schedulerService, UserService userService) {
        this.schedulerService = schedulerService;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable int id, @RequestHeader HttpHeaders token) {
        String providerId = UserDetailsFilter.getUserInfo(token, "sub");
        return ResponseEntity.ok(schedulerService.getAppointmentById(id, providerId));
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments(@RequestHeader HttpHeaders token) {
        String providerId = UserDetailsFilter.getUserInfo(token, "sub");
        return ResponseEntity.ok(schedulerService.getAllAppointmentByProvider(providerId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Appointment>> getAppointmentByCustomerId(@PathVariable int customerId, @RequestHeader HttpHeaders token) {
        String providerId = UserDetailsFilter.getUserInfo(token, "sub");
        return ResponseEntity.ok(schedulerService.getAppointmentsByCustomerId(customerId, providerId));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createAppointment(@RequestHeader HttpHeaders token, @RequestBody AppointmentDto appointmentDto) {
        appointmentDto.setProviderId(UserDetailsFilter.getUserInfo(token, "sub"));
        schedulerService.createAppointment(appointmentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    @PutMapping("/update/{id}/{status}")
//    public ResponseEntity<User> updateStatus(@RequestHeader HttpHeaders token, @PathVariable int id, @PathVariable String status) {
//        String userId = UserDetailsFilter.getUserInfo(token, "sub");
//        User user = userService.findUserById(userId);
//        List<Appointment> appointmentsByUser = schedulerService.getAppointmentsByProviderId(user.getId());
//        if (!appointmentsByUser.isEmpty()) {
//            Appointment foundAppointment = appointmentsByUser.stream().filter(i -> i.getId() == id).findFirst().orElseThrow(AppointmentNotFoundException::new);
//            schedulerService.updateStatus(foundAppointment, AppointmentStatus.valueOf(status));
//        }
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
}
