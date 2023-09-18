package com.godel.employeemanagementrestful.controller;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.godel.employeemanagementrestful.dto.UserDTO;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.exceptions.ResourceNotFoundException;
import com.godel.employeemanagementrestful.repository.UserRepository;
import com.godel.employeemanagementrestful.service.TimetableService;
import com.godel.employeemanagementrestful.service.UserService;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;
	
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
	public User saveUser(@RequestBody User user) {
		return userService.saveUser(user);
	}
	
	@PostMapping("/{userId}/generateTimetable")
	public void generateTimeTable(@PathVariable Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
		timetableService.populateTimetable(
	        		user, LocalDate.now().minusYears(1), 
	        		LocalDate.now().plusYears(1));
	}
	
	@PutMapping("/{userId}")
	public User editUser(@RequestBody User user) {
		return userRepository.save(user);
	}	
	
    @GetMapping("/by-email")
    public ResponseEntity<UserDTO> fetchUserByEmail(@RequestParam String emailId) {
        Optional<User> userOptional = userRepository.findByEmailId(emailId);
        
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        User user = userOptional.get();
        UserDTO userDTO = new UserDTO(user);
        
        return ResponseEntity.ok(userDTO);
    }
	
}
