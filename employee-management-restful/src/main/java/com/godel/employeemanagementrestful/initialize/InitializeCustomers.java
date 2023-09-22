package com.godel.employeemanagementrestful.initialize;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.godel.employeemanagementrestful.entity.Customer;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.initialize.generator.PersonGenerator;
import com.godel.employeemanagementrestful.repository.CustomerRepository;
import com.godel.employeemanagementrestful.repository.UserRepository;

@Component
public class InitializeCustomers {
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	UserRepository userRepository;

	public void saveCustomers(int numberOfCustomers) {
        Set<String> emails = new HashSet<>();
        List<User> existingUsers = userRepository.findAll();
        for (User existingUser : existingUsers) {
            emails.add(existingUser.getEmailId());
        }
		for (int i=0;i<numberOfCustomers;i++) {
				Customer customer = PersonGenerator.generateCustomer();
				if (emails.contains(customer.getEmailId())) {
					i--;
					continue;
				}
				customerRepository.save(customer);
			}
	}
		

}
