package com.godel.employeemanagementrestful.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godel.employeemanagementrestful.entity.Timetable;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.repository.TimetableRepository;

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
	
    public List<Timetable> getFilteredTimetable(Long userId, LocalDate after, LocalDate before) {
        if (userId == null && after == null && before == null) {
            return timetableRepository.findAll();
        }
        if (userId == null) {
            return timetableRepository.findByDateBetween(
                    LocalDateTime.of(after, LocalTime.MIN),
                    LocalDateTime.of(before, LocalTime.MAX));
        }
        if (after == null && before == null) {
            return timetableRepository.findByUserUserId(userId);
        }
        return timetableRepository.findByUserUserIdAndDateBetween(
                userId,
                LocalDateTime.of(after, LocalTime.MIN),
                LocalDateTime.of(before, LocalTime.MAX));
    }

}
