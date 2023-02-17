package com.godel.employeemanagementrestful.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.godel.employeemanagementrestful.entity.WorkOrder;

@SpringBootTest
class WorkOrderServiceTest {

	@Autowired
	private WorkOrderService workOrderService;

	
	@SuppressWarnings("removal")
	@Test
	void assignUserToWorkOrderTest() {
		workOrderService.assignUserToWorkOrder(new Long(1),new Long(100000));

	}
	
	@Test
	public void getWorkOrdersByUser(){
		List<WorkOrder> workOrderList = workOrderService.getWorkOrdersByUser(new Long(1));
		System.out.println("userList = " + workOrderList);
	
	}	
	
}
