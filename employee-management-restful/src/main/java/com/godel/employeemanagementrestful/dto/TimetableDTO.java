package com.godel.employeemanagementrestful.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import com.godel.employeemanagementrestful.entity.Timetable;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.enums.OfficeCode;

import lombok.Data;

@Data
public class TimetableDTO {
	
	private Long timetableId;
	private LocalDate date;
	private LocalTime checkIn;
	private LocalTime checkOut;
	private String hoursWorked;
	private Duration trainingTime;
	private Boolean onLeave;
	private Long userId;
	private String userEmail;
	private String userFirstName;
	private String userLastName;
	private OfficeCode officeCode;
	
	public TimetableDTO(Timetable timetable) {
		this.timetableId = timetable.getTimetableId();
		this.date = timetable.getDate();
		this.checkIn = timetable.getCheckIn();
		this.checkOut = timetable.getCheckOut();
		this.onLeave = timetable.getOnLeave();
		this.trainingTime = timetable.getTrainingTime();
		this.hoursWorked = formatDuration(timetable.getTimeWorked());


        User user = timetable.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.userEmail = user.getEmailId();
            this.userFirstName = user.getFirstName();
            this.userLastName = user.getLastName();
            this.officeCode = user.getOfficeCode();
        }
		
	}
	
	private String formatDuration(Duration duration) {
		long hours = duration.toHours();
		long minutes = duration.toMinutesPart();
		long seconds = duration.toSecondsPart();
		return String.format("%.2f", hours + ((double) minutes / 60) + ((double) seconds / 3600));
	}	
	
	
}


