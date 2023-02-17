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
	private Duration workedHours;
	private Long userId;
	private String assigneeEmail;
	
	public TimetableDTO(Timetable timetable) {
		this.timetableId = timetable.getTimetableId();
		this.date = timetable.getDate();
		this.checkIn = timetable.getCheckIn();
		this.checkOut = timetable.getCheckOut();
		this.workedHours = timetable.getWorkedHours();

        User user = timetable.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.assigneeEmail = user.getEmailId();
        }
		
	}
	
}


