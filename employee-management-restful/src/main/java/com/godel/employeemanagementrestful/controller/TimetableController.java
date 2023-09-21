package com.godel.employeemanagementrestful.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.godel.employeemanagementrestful.dto.TimetableDTO;
import com.godel.employeemanagementrestful.entity.Timetable;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.exceptions.TimetableException;
import com.godel.employeemanagementrestful.repository.UserRepository;
import com.godel.employeemanagementrestful.service.TimetableService;

@RestController
@RequestMapping("/api/v1/timetables")
public class TimetableController {
	
	@Autowired
	TimetableService timetableService;
	
	@Autowired
	UserRepository userRepository;

	
	@GetMapping("")
    public ResponseEntity<List<TimetableDTO>> getFilteredTimetables(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate after,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate before) {
        List<Timetable> timetables = timetableService.getFilteredTimetable(userId, after, before);
        List<TimetableDTO> timetableDTOS = timetables.stream()
                .map(workOrder -> new TimetableDTO(workOrder))
                .collect(Collectors.toList());
        return ResponseEntity.ok(timetableDTOS);
	}
	@PutMapping("{userId}/checkin")
	public ResponseEntity<String> checkIn(@PathVariable Long userId) {
		
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String authenticatedEmail = authentication.getName();
	    User user = userRepository.findByEmailId(authenticatedEmail).orElse(null);
	    if (user == null || !user.getUserId().equals(userId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You can't check in for another user.");
	    }
		try {
			timetableService.checkIn(userId);
			return ResponseEntity.ok("Checked in successfully");
		}
		catch (TimetableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}	
	
	@PutMapping("{userId}/checkout")
	public ResponseEntity<String> checkOut(@PathVariable Long userId) {
		try {
			timetableService.checkOut(userId);
			return ResponseEntity.ok("Checked out successfully");
		}
		catch (TimetableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@GetMapping("{userId}/checkin")
	public ResponseEntity<LocalTime> getCheckIn(@PathVariable Long userId) {
	    LocalTime checkInTime = timetableService.getTodayCheckInTime(userId);
	    return ResponseEntity.ok(checkInTime);
	}

	@GetMapping("{userId}/checkout")
	public ResponseEntity<LocalTime> getCheckOut(@PathVariable Long userId) {
	    LocalTime checkOutTime = timetableService.getTodayCheckOutTime(userId);
	    return ResponseEntity.ok(checkOutTime);
	}
	
	@PreAuthorize("hasAnyAuthority('OPERATOR', 'ADMIN')")
	@PutMapping("{userId}/{date}/editTimes")
	public ResponseEntity<String> editCheckTimesForDate(@PathVariable Long userId, 
	        @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, 
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime checkIn,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime checkOut) {
	    if (checkIn == null || checkOut == null) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both check-in and check-out times are required");
		}
		if (checkIn.isAfter(checkOut)) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Check-in time must be before check-out time");
		}
		timetableService.editCheckTimesForDate(userId, date, checkIn, checkOut);
		return ResponseEntity.ok("Check-in and check-out times edited successfully for the specified date");
	}	

}
