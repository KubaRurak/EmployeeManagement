package com.godel.employeemanagementrestful.service;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.repository.WorkOrderRepository;

@SpringBootTest
class WorkOrderServiceTest {

	@Autowired
	private WorkOrderService workOrderService;
	
	@Autowired
	private WorkOrderRepository workOrderRepository;

	
//	@SuppressWarnings("removal")
//	@Test
//	void assignUserToWorkOrderTest() {
//		workOrderService.assignUserToWorkOrder(new Long(1),new Long(100000));
//
//	}
	@SuppressWarnings("removal")
	@Test
	void completeWorkOrderTest() {
		Optional<WorkOrder> optionalWorkOrder = workOrderRepository.findById(new Long(100000));
	    if (optionalWorkOrder.isPresent()) {
	        WorkOrder workOrder = optionalWorkOrder.get();
	        workOrderService.completeWorkOrder(workOrder);
	    }

	}	
	
	
//	@Test
//	public void getWorkOrdersByUser(){
//		List<WorkOrder> workOrderList = workOrderService.getWorkOrdersByUser(new Long(1));
//		System.out.println("userList = " + workOrderList);
//	
//	}	
	
}
