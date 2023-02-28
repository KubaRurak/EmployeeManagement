package com.godel.employeemanagementrestful.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.initialize.generate.PersonGenerator;
import com.godel.employeemanagementrestful.service.UserService;


public class InitializeUsers {
	
	@Autowired
	UserService userService;
	
	public void saveUsers(int numberOfUsers) {
		for (int i=0;i<numberOfUsers;i++) {
			User user = PersonGenerator.generateUser();
			userService.saveUser(user);
		}
	}

}
