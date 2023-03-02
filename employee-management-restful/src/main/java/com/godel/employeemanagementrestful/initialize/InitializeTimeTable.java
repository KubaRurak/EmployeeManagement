package com.godel.employeemanagementrestful.initialize;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.godel.employeemanagementrestful.entity.Timetable;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.repository.TimetableRepository;
import com.godel.employeemanagementrestful.service.PayrollService;

@Component
public class InitializeTimeTable {
	
	@Autowired
	TimetableRepository timetableRepository;
	
	@Autowired
	PayrollService payrollService;
	
	public void populateTimetable(LocalDate startDate, LocalDate endDate) {
	    List<Timetable> timetables = timetableRepository.findByDateBetween(
	        startDate, endDate);
	    for (Timetable timetable : timetables) {
	        // Skip weekends
	        if (timetable.getDate().getDayOfWeek() == DayOfWeek.SATURDAY ||
	                timetable.getDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
	            continue;
	        }
	        Long userId = timetable.getUser().getUserId();

	        // Set random check-in time between 7:00am and 10:00am
	        timetable.setCheckIn(LocalTime.of(7 + new Random().nextInt(3), new Random().nextInt(60)));

	        // Set random check-out time after 7-9 hours of work
	        LocalTime checkOutTime = timetable.getCheckIn().plusHours(7 + new Random().nextInt(3));
	        timetable.setCheckOut(checkOutTime);
	        payrollService.updatePayrollTime(userId,timetable);
	    }

	    timetableRepository.saveAll(timetables);
	}
}



