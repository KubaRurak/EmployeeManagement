package com.godel.employeemanagementrestful.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godel.employeemanagementrestful.entity.Timetable;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.exceptions.TimetableException;
import com.godel.employeemanagementrestful.repository.TimetableRepository;

@Service
public class TimetableService {
	
	@Autowired
	TimetableRepository timetableRepository;
	
	@Autowired
	PayrollService payrollService;
	
	
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
	        return timetableRepository.findByDateBetween(after, before);
	    }
	    if (after == null && before == null) {
	        return timetableRepository.findByUserUserId(userId);
	    }
	    return timetableRepository.findByUserUserIdAndDateBetween(userId,after,before);
	}	
//    public List<Timetable> getFilteredTimetable(Long userId, LocalDate after, LocalDate before) {
//        if (userId == null && after == null && before == null) {
//            return timetableRepository.findAll();
//        }
//        if (userId == null) {
//            return timetableRepository.findByDateBetween(
//                    LocalDateTime.of(after, LocalTime.MIN),
//                    LocalDateTime.of(before, LocalTime.MAX));
//        }
//        if (after == null && before == null) {
//            return timetableRepository.findByUserUserId(userId);
//        }
//        return timetableRepository.findByUserUserIdAndDateBetween(
//                userId,
//                LocalDateTime.of(after, LocalTime.MIN),
//                LocalDateTime.of(before, LocalTime.MAX));
//    }
    
    public void checkIn(Long userId) throws TimetableException {
        LocalTime now = LocalTime.now();
        LocalDate currentDate = LocalDate.now();
        Timetable timetable = timetableRepository.findByUserUserIdAndDate(userId, currentDate);
        if (timetable.getCheckIn() != null) {
            throw new TimetableException("User has already checked in for today");
        } else {
        	timetable.setCheckIn(now);
        	System.out.println("checked in");
        }
        timetableRepository.save(timetable);
    }
    
    public void checkOut(Long userId) throws TimetableException {
        LocalTime now = LocalTime.now();
        LocalDate currentDate = LocalDate.now();
        Timetable timetable = timetableRepository.findByUserUserIdAndDate(userId, currentDate);
        if (timetable.getCheckOut() != null) {
            throw new TimetableException("User has already checked out for today");
        }
        else if (timetable.getCheckIn() == null) {
            throw new TimetableException("User hasn't checked in");        	
        }
        else {
        	timetable.setCheckOut(now);
        	System.out.println("checked out");
        }
//        payrollService.updatePayrollTime(timetable);
        timetableRepository.save(timetable);
    }    
    

}
