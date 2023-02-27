package com.godel.employeemanagementrestful.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godel.employeemanagementrestful.dto.UserDTO;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.repository.UserRepository;

@Service
public class UserService {
	
//	@Autowired
//	private PayrollService payrollService;
	
	@Autowired
	private TimetableService timetableService;
	
	@Autowired
	private UserRepository userRepository;
	
	public User saveUser(User user) {
		User savedUser = userRepository.save(user);
		timetableService.populateTimetable(
        		savedUser, LocalDate.now().minusYears(1), 
        		LocalDate.now().plusYears(1));
//		payrollService.generatePayrollForUser(null, null);
		return savedUser;
	}



}
