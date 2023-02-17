package com.godel.employeemanagementrestful.controller;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godel.employeemanagementrestful.dto.UserDTO;
import com.godel.employeemanagementrestful.dto.WorkOrderDTO;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.exceptions.ResourceNotFoundException;
import com.godel.employeemanagementrestful.repository.UserRepository;
import com.godel.employeemanagementrestful.repository.WorkOrderRepository;
import com.godel.employeemanagementrestful.service.TimetableService;

import jakarta.transaction.Transactional;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TimetableService timetableService;

	@GetMapping("/full")
	public List<User> fetchUserListFull() {
		return userRepository.findAll();
	}
	
	@GetMapping("")
	public List<UserDTO> fetchUserList() {
		List<User> users = userRepository.findAll();
		List<UserDTO> userDTOs = users.stream()
	            .map(user -> new UserDTO(user))
	            .collect(Collectors.toList());
		return userDTOs;
	}

	@GetMapping("/{userId}")
	public UserDTO fetchUserById(@PathVariable Long userId) {
	    User user = userRepository.findById(userId)
	    		.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));;
	    UserDTO userDTO = new UserDTO(user);
	    return userDTO;

	}

	@PostMapping("")
	public UserDTO saveUser(@RequestBody UserDTO userDTO) {
	    User user = new User();
	    user.setFirstName(userDTO.getFirstName());
	    user.setLastName(userDTO.getLastName());
	    user.setEmailId(userDTO.getEmailId());
	    User savedUser = userRepository.save(user);
		timetableService.populateTimetable(
        		savedUser, LocalDate.now().minusYears(1), 
        		LocalDate.now().plusYears(1));
	    return new UserDTO(savedUser);
	}
	
	@PostMapping("/{userId}/generateTimetable")
	public void generateTimeTable(@PathVariable Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
		timetableService.populateTimetable(
	        		user, LocalDate.now().minusYears(1), 
	        		LocalDate.now().plusYears(1));
	}


	
//	@PostMapping("")
//	public User saveUser(@RequestBody User user) {
//	    return userRepository.save(user);
//	}
	
	@PutMapping("/{userId}")
	public User editUser(@RequestBody User user) {
		return userRepository.save(user);
	}	
	
}
