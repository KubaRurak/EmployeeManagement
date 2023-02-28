package com.godel.employeemanagementrestful.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import com.godel.employeemanagementrestful.entity.Timetable;
import com.godel.employeemanagementrestful.entity.User;

import lombok.Data;

@Data
public class TimetableDTO {
	
	private Long timetableId;
	private LocalDate date;
	private LocalTime checkIn;
	private LocalTime checkOut;
	private Duration timeWorked;
	private Long userId;
	private String userEmail;
	private String userFirstName;
	private String userLastName;
	
	public TimetableDTO(Timetable timetable) {
		this.timetableId = timetable.getTimetableId();
		this.date = timetable.getDate();
		this.checkIn = timetable.getCheckIn();
		this.checkOut = timetable.getCheckOut();
		this.timeWorked = timetable.getTimeWorked();

        User user = timetable.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.userEmail = user.getEmailId();
            this.userFirstName = user.getFirstName();
            this.userLastName = user.getLastName();
        }
		
	}
	
}


