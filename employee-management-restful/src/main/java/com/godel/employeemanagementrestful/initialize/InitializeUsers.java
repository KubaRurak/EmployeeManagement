package com.godel.employeemanagementrestful.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.initialize.generate.UserGenerator;
import com.godel.employeemanagementrestful.service.UserService;

@Component
@Profile("data-init")
public class InitializeUsers implements CommandLineRunner {
	
	private final int numberOfUsers=20;
	
	@Autowired
	UserService userService;
	
	public void saveUsers() {
		for (int i=0;i<numberOfUsers;i++) {
			User user = UserGenerator.generateUser();
			userService.saveUser(user);
		}
	}

	@Override
	public void run(String... args) throws Exception {
		this.saveUsers();
	}

}
