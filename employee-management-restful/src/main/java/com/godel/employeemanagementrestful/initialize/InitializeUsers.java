package com.godel.employeemanagementrestful.initialize;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.enums.Role;
import com.godel.employeemanagementrestful.initialize.generator.PersonGenerator;
import com.godel.employeemanagementrestful.repository.UserRepository;
import com.godel.employeemanagementrestful.service.UserService;

@Component
public class InitializeUsers {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;
	
    public void saveUsers(int numberOfUsers) {
        Set<String> emails = new HashSet<>();

        // If not the first initialization, seed the HashSet with existing emails
        List<User> existingUsers = userRepository.findAll();
        for (User existingUser : existingUsers) {
            emails.add(existingUser.getEmailId());
        }
        int begin = 0;
        if (existingUsers.size()==0) {
	        User adminUser = PersonGenerator.generateSpecificUser(Role.Admin);
	        User operatorUser = PersonGenerator.generateSpecificUser(Role.Operator);
	        User engineerUser = PersonGenerator.generateSpecificUser(Role.Engineer);
	
	        emails.add(adminUser.getEmailId());
	        emails.add(operatorUser.getEmailId());
	        emails.add(engineerUser.getEmailId());
	
	        userService.saveUser(adminUser);
	        userService.saveUser(operatorUser);
	        userService.saveUser(engineerUser);
	        begin = 3;
        }
        for (int i = begin; i < numberOfUsers; i++) { 
            User user = PersonGenerator.generateUser();
            String email = user.getEmailId();
            if (emails.contains(email)) continue;
            else emails.add(email);
            userService.saveUser(user);
        }
    }

}
