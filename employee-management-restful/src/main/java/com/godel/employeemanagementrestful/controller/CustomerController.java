package com.godel.employeemanagementrestful.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godel.employeemanagementrestful.dto.CustomerDTO;
import com.godel.employeemanagementrestful.entity.Customer;
import com.godel.employeemanagementrestful.repository.CustomerRepository;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("")
	public List<CustomerDTO> getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
		List<CustomerDTO> customerDTOs = customers.stream()
	            .map(customer -> new CustomerDTO(customer))
	            .collect(Collectors.toList());
		return customerDTOs;
	}
//	@PostMapping("")
//	public Customer saveCustomer(@RequestBody Customer customer) {
//		return customerRepository.save(customer);
//	}
//	
//	@PutMapping("/{id}")
//	public Customer editCustomer(@RequestBody Customer customer) {
//		return customerRepository.save(customer);
//	}	

}
