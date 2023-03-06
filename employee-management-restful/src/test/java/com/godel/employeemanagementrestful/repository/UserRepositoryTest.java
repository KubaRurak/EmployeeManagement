package com.godel.employeemanagementrestful.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.enums.OfficeCode;
import com.godel.employeemanagementrestful.enums.Role;

import jakarta.transaction.Transactional;

@SpringBootTest
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void saveuser1() {
		User user = User.builder()
				.firstName("Godel")
				.lastName("Rurak")
				.role(Role.Operator)
				.officeCode(OfficeCode.KRK)
				.isEmployed(true)
				.emailId("caa@bbb.com")
				.build();
		
		userRepository.save(user);
	}
	@Transactional
	@Test
	public void printAllusers() {
		List<User> userList = 
				userRepository.findAll();
		
		System.out.println("userList = " + userList);
	}
}

