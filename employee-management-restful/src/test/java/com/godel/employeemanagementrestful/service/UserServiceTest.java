package com.godel.employeemanagementrestful.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.enums.OfficeCode;

@SpringBootTest
class UserServiceTest {
	
	@Autowired
	UserService userService;

	@Test
	void createUser() {
		User user = User.builder()
				.firstName("Godel")
				.lastName("Rurak")
				.role("Engingeer")
				.officeCode(OfficeCode.KRK)
				.isEmployed(true)
				.emailId("abc@googlecom")
				.build();
		userService.saveUser(user);

	}

}
