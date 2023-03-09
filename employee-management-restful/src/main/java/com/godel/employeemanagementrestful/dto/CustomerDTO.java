package com.godel.employeemanagementrestful.dto;

import com.godel.employeemanagementrestful.entity.Customer;

import lombok.Data;

@Data
public class CustomerDTO {
	
	private Long customerId;
	private String customerEmail;
	private String customerFirstName;
	private String customerLastName;
	private String customerCompanyName;
	
	public CustomerDTO(Customer customer) {
		this.customerId=customer.getCustomerId();
		this.customerEmail=customer.getEmailId();
		this.customerFirstName=customer.getFirstName();
		this.customerLastName=customer.getLastName();
		this.customerCompanyName=customer.getCompanyName();
	}
}
