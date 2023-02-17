package com.godel.employeemanagementrestful.repository;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.godel.employeemanagementrestful.entity.OfficeCode;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;

@SpringBootTest
class WorkOrderRepositoryTest {

	@Autowired
	private WorkOrderRepository workOrderRepository;
	
	@Test
	public void saveWorkOrder() {
		
//				User user = User.builder()
//				.firstName("Kuba2")
//				.lastName("Rurak2")
//				.role("Projektant")
//				.officeCode(OfficeCode.WAW)
//				.isEmployed(true)
//				.emailId("Godello3@bbb.com")
//				.build();		
				
		WorkOrder workOrder = WorkOrder.builder()
				.orderName("WAR0005")
				.price(new BigDecimal("3000"))
				.completed(false)
				.canceled(false)
				.startTimeStamp(LocalDateTime.now())
				.comments("third workorder")
				.user(null)
				.build();
		
		workOrderRepository.save(workOrder);
	}
	@Test
	public void saveWorkOrderWithUser() {
		
		User user = User.builder()
		.firstName("Kuba2")
		.lastName("Rurak2")
		.role("Projektant")
		.officeCode(OfficeCode.WAW)
		.isEmployed(true)
		.emailId("Godello3@bbb.com")
		.build();		
		
		WorkOrder workOrder = WorkOrder.builder()
				.orderName("WAR0006")
				.price(new BigDecimal("3000"))
				.completed(false)
				.canceled(false)
				.startTimeStamp(LocalDateTime.now())
				.comments("fourth workorder")
				.user(user)
				.build();
		
		workOrderRepository.save(workOrder);
	}
}
