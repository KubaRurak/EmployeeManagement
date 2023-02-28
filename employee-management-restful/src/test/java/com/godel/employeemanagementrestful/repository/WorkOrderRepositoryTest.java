package com.godel.employeemanagementrestful.repository;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.godel.employeemanagementrestful.entity.OrderType;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.enums.OfficeCode;
import com.godel.employeemanagementrestful.enums.UserRole;

@SpringBootTest
class WorkOrderRepositoryTest {

	@Autowired
	private WorkOrderRepository workOrderRepository;
	
	@Autowired
	private OrderTypeRepository orderTypeRepository;
	
	@Test
	public void saveWorkOrder() {
		
		Optional<OrderType> orderTypeOptional = orderTypeRepository.findById(new Long(1));
		OrderType orderType = null;
	    if (orderTypeOptional.isPresent()) {
	        orderType = orderTypeOptional.get();
	    }
		WorkOrder workOrder = WorkOrder.builder()
				.orderName("WAR0001")
				.orderType(orderType)
				.comments("Jakiś tam workOrder")
				.user(null)
				.build();
		
		workOrderRepository.save(workOrder);
	}
	@Test
	public void saveWorkOrderWithUser() {
		
		User user = User.builder()
		.firstName("Kuba2")
		.lastName("Rurak2")
		.role(UserRole.Engineer)
		.officeCode(OfficeCode.WAW)
		.isEmployed(true)
		.emailId("Godello3@bbb.com")
		.build();		
		
		WorkOrder workOrder = WorkOrder.builder()
				.orderName("WAR0006")
				.orderType(new OrderType())
				.startTimeStamp(LocalDateTime.now())
				.comments("fourth workorder")
				.user(user)
				.build();
		
		workOrderRepository.save(workOrder);
	}
}
