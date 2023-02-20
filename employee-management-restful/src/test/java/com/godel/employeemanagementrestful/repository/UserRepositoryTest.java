package com.godel.employeemanagementrestful.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.enums.OfficeCode;

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
				.role("Engingeer")
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
	@Test
	public void saveuser2() {
		
	User user2 = User.builder()
			.firstName("Tadzio")
			.lastName("Rurak")
			.role("Szef")
			.officeCode(OfficeCode.WAW)
			.isEmployed(true)
			.emailId("Tadzio@bbb.com")
			.build();
	
	userRepository.save(user2);
	}
	
//	@Test
//	public void printuserById() {
//		
//		List<user> users = 
//				userRepository.findByuserId((long) 1);
//		
//		System.out.println("users = " + users);
//	}
}
//	@Test
//	public void saveuser() {
//		
//		WorkOrder workOrder1 = WorkOrder.builder()
//				.orderName("WAR0002")
//				.price(new BigDecimal("2300"))
//				.completed(false)
//				.canceled(false)
//				.startTimeStamp(LocalDate.now())
//				.comments("2nd workorder")
//				.build();
//		
//		WorkOrder workOrder2 = WorkOrder.builder()
//				.orderName("WAR0003")
//				.price(new BigDecimal("2300"))
//				.completed(false)
//				.canceled(false)
//				.startTimeStamp(LocalDate.now())
//				.comments("3rd workorder")
//				.build();	
//		
//		user user = user.builder()
//				.firstName("Kuba")
//				.lastName("Rurak")
//				.role("Projektant")
//				.officeCode(OfficeCode.WAW)
//				.isEmployed(true)
//				.emailId("Godello@bbb.com")
//				.workOrder(List.of(workOrder1,workOrder2))
//				.build();
//	
//		userRepository.save(user);
//	}
//	


