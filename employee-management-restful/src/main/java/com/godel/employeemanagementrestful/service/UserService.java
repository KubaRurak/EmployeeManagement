package com.godel.employeemanagementrestful.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godel.employeemanagementrestful.dto.UserDTO;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.exceptions.ResourceNotFoundException;
import com.godel.employeemanagementrestful.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	
	@Autowired
	private PayrollService payrollService;
	
	@Autowired
	private TimetableService timetableService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User saveUser(User user) {
	    userRepository.save(user);
	    Optional<User> optionalUser = userRepository.findById(user.getUserId());
	    if (optionalUser.isPresent()) {
	        User savedUser = optionalUser.get();
			timetableService.populateTimetable(
	        		savedUser, LocalDate.of(2022,2,1), 
	        		LocalDate.now().plusYears(1));
			payrollService.generatePayrollForUser(YearMonth.of(2022, 2), 24, savedUser.getUserId());
			return savedUser;
	    } else {
	        throw new ResourceNotFoundException("User not found with id");
	    }

	}



}
