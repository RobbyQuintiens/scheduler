package com.example.scheduler.controller;

import com.example.scheduler.dto.AppointmentDto;
import com.example.scheduler.exception.AppointmentNotFoundException;
import com.example.scheduler.filter.UserDetailsFilter;
import com.example.scheduler.model.Appointment;
import com.example.scheduler.model.AppointmentStatus;
import com.example.scheduler.model.User;
import com.example.scheduler.service.SchedulerService;
import com.example.scheduler.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable int id) {
        return ResponseEntity.ok(schedulerService.getAppointmentById(id));
    }

    @GetMapping("/attend")
    public ResponseEntity<List<Appointment>> getAppointmentByCustomerId(@RequestHeader HttpHeaders token) {
        String id = UserDetailsFilter.getUserInfo(token, "sub");
        User user = userService.findUserById(id);
        return ResponseEntity.ok(schedulerService.getAppointmentsByCustomerId(user.getId()));
    }

    @GetMapping("/scheduled")
    public ResponseEntity<List<Appointment>> getAppointmentByProviderId(@RequestHeader HttpHeaders token) {
        String id = UserDetailsFilter.getUserInfo(token, "sub");
        User user = userService.findUserById(id);
        return ResponseEntity.ok(schedulerService.getAppointmentsByProviderId(user.getId()));
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createAppointment(@RequestHeader HttpHeaders token, @RequestBody AppointmentDto appointmentDto) {
        appointmentDto.setProviderId(UserDetailsFilter.getUserInfo(token, "sub"));
        schedulerService.createAppointment(appointmentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}/{status}")
    public ResponseEntity<User> updateStatus(@RequestHeader HttpHeaders token, @PathVariable int id, @PathVariable String status) {
        String userId = UserDetailsFilter.getUserInfo(token, "sub");
        User user = userService.findUserById(userId);
        List<Appointment> appointmentsByUser = schedulerService.getAppointmentsByProviderId(user.getId());
        if (!appointmentsByUser.isEmpty()) {
            Appointment foundAppointment = appointmentsByUser.stream().filter(i -> i.getId() == id).findFirst().orElseThrow(AppointmentNotFoundException::new);
            schedulerService.updateStatus(foundAppointment, AppointmentStatus.valueOf(status));
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
