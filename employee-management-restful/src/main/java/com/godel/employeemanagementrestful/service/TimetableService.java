package com.godel.employeemanagementrestful.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.repository.TimetableRepository;
import com.godel.employeemanagementrestful.repository.UserRepository;
import com.godel.employeemanagementrestful.entity.Timetable;

@Service
public class TimetableService {
	
	@Autowired
	TimetableRepository timetableRepository;
	
	
	public void populateTimetable(User user, LocalDate startDate, LocalDate endDate) {
		for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
		    Timetable entry = new Timetable();
		    entry.setUser(user);
		    entry.setDate(date);
		    entry.setCheckIn(null);
		    entry.setCheckOut(null);
		    timetableRepository.save(entry);
		}
	}

}
