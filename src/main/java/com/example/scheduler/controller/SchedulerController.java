package com.example.scheduler.controller;

import com.example.scheduler.dto.AppointmentDto;
import com.example.scheduler.filter.UserDetailsFilter;
import com.example.scheduler.model.Appointment;
import com.example.scheduler.model.User;
import com.example.scheduler.service.SchedulerService;
import com.example.scheduler.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

//    @GetMapping("/all")
//    public ResponseEntity<List<Appointment>> getAllAppointments(@AuthenticationPrincipal CustomUserDetails user) {
//        if (user.hasRole("ROLE_CUSTOMER")) {
//            return ResponseEntity.ok(schedulerService.getAppointmentsByCustomerId(user.getId()));
//        } else if (user.hasRole("ROLE_PROVIDER")) {
//            return ResponseEntity.ok(schedulerService.getAppointmentsByProviderId(user.getId()));
//        }
//        return null;
//    }

    @GetMapping("/test")
    public ResponseEntity<List<String>> getAllAppointments(@RequestHeader HttpHeaders token) {
        UserDetailsFilter userNameFilter = new UserDetailsFilter();
        List<String> info = new ArrayList<>();
        String email = userNameFilter.getUserInfo(token, "email");
        String name = userNameFilter.getUserInfo(token, "name");
        info.add(email);
        info.add(name);
        return ResponseEntity.ok(info);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createAppointment(@RequestHeader HttpHeaders token, @RequestBody AppointmentDto appointmentDto) {
        UserDetailsFilter userNameFilter = new UserDetailsFilter();
        appointmentDto.setProviderId(userNameFilter.getUserInfo(token, "sub"));
        schedulerService.createAppointment(appointmentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.findById(id));
    }
}
