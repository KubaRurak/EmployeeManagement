package com.godel.employeemanagementrestful.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godel.employeemanagementrestful.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
