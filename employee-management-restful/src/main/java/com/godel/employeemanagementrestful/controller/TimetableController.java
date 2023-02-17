package com.godel.employeemanagementrestful.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.godel.employeemanagementrestful.dto.TimetableDTO;
import com.godel.employeemanagementrestful.entity.Timetable;
import com.godel.employeemanagementrestful.service.TimetableService;

@RestController
@RequestMapping("/api/v1/timetable")
public class TimetableController {
	
	@Autowired
	TimetableService timetableService;
	
	@GetMapping("")
    public ResponseEntity<List<TimetableDTO> getFilteredTimetable(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate after,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate before) {
        List<Timetable> timetables = timetableService.getFilteredTimetable(userId, after, before);
        List<TimetableDTO> timetableDTOS = timetables.stream()
                .map(timetable -> new TimetableDTO(timetable))
                .collect(Collectors.toList());
        return ResponseEntity.ok(timetableDTOS);
	}

}
