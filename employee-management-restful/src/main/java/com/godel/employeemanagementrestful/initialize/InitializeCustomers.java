package com.godel.employeemanagementrestful.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.godel.employeemanagementrestful.entity.Customer;
import com.godel.employeemanagementrestful.initialize.generator.PersonGenerator;
import com.godel.employeemanagementrestful.repository.CustomerRepository;

@Component
public class InitializeCustomers {
	
	@Autowired
	CustomerRepository customerRepository;

	public void saveCustomers(int numberOfCustomers) {
		
		for (int i=0;i<numberOfCustomers;i++) {
				Customer customer = PersonGenerator.generateCustomer();
				customerRepository.save(customer);
			}
	}
		

}
