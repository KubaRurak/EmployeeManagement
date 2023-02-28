package com.godel.employeemanagementrestful.initialize;

import org.springframework.beans.factory.annotation.Autowired;

import com.godel.employeemanagementrestful.entity.Customer;
import com.godel.employeemanagementrestful.initialize.generate.PersonGenerator;
import com.godel.employeemanagementrestful.repository.CustomerRepository;

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
