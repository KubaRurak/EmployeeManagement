package com.godel.employeemanagementrestful.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.godel.employeemanagementrestful.dto.TimetableDTO;
import com.godel.employeemanagementrestful.entity.Timetable;
import com.godel.employeemanagementrestful.exceptions.TimetableException;
import com.godel.employeemanagementrestful.service.TimetableService;

@RestController
@RequestMapping("/api/v1/timetables")
public class TimetableController {
	
	@Autowired
	TimetableService timetableService;

	
	@GetMapping("")
    public ResponseEntity<List<TimetableDTO>> getFilteredTimetables(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate after,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate before) {
        List<Timetable> timetables = timetableService.getFilteredTimetable(userId, after, before);
        List<TimetableDTO> workOrderDTOS = timetables.stream()
                .map(workOrder -> new TimetableDTO(workOrder))
                .collect(Collectors.toList());
        return ResponseEntity.ok(workOrderDTOS);
	}
	
	@PutMapping("{userId}/checkin")
	public ResponseEntity<String> checkIn(@PathVariable Long userId) {
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
			return ResponseEntity.ok("Checked in successfully");
		}
		catch (TimetableException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}		
	
	

}
