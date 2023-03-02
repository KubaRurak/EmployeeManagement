package com.godel.employeemanagementrestful.initialize;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.initialize.generator.PersonGenerator;
import com.godel.employeemanagementrestful.service.UserService;

@Component
public class InitializeUsers {
	
	@Autowired
	UserService userService;
	
	public void saveUsers(int numberOfUsers) {
		Set<String> emails = new HashSet<>();
		for (int i=0; i<numberOfUsers;i++) {
			User user = PersonGenerator.generateUser();
			String email = user.getEmailId();
			if (emails.contains(email)) continue;
			else emails.add(email);
			userService.saveUser(user);
		}
	}

}
