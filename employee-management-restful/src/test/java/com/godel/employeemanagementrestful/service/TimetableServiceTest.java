package com.godel.employeemanagementrestful.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.godel.employeemanagementrestful.exceptions.TimetableException;

@SpringBootTest
class TimetableServiceTest {
	
	@Autowired
	TimetableService timetableService;

	@SuppressWarnings("removal")
	@Test
	void CheckIn() {
		try {
			timetableService.checkIn(new Long(1));
		} catch (TimetableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("removal")
	@Test
	void CheckOut() {
		try {
			timetableService.checkOut(new Long(2));
		} catch (TimetableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
