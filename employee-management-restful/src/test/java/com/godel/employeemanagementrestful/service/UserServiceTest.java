package com.godel.employeemanagementrestful.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.enums.OfficeCode;
import com.godel.employeemanagementrestful.enums.Role;

@SpringBootTest
class UserServiceTest {
	
	@Autowired
	UserService userService;

	@Test
	void createUser() {
		User user = User.builder()
				.firstName("Godel")
				.lastName("Rurak")
				.role(Role.Engineer)
				.officeCode(OfficeCode.KRK)
				.isEmployed(true)
				.emailId("abc@googlecom")
				.build();
		userService.saveUser(user);

	}

}
