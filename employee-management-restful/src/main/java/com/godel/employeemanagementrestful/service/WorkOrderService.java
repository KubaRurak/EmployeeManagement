package com.godel.employeemanagementrestful.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.repository.UserRepository;
import com.godel.employeemanagementrestful.repository.WorkOrderRepository;

@Service
public class WorkOrderService{
	
	@Autowired
	private WorkOrderRepository workOrderRepository;
	@Autowired
	private UserRepository userRepository;
//	@Autowired
//	private PayrollService payrollService;


    public List<WorkOrder> getFilteredWorkOrders(Long userId, LocalDate after, LocalDate before) {
        if (userId == null && after == null && before == null) {
            return workOrderRepository.findAll();
        }
        if (userId == null) {
            return workOrderRepository.findByLastModificationTimeStampBetween(
                    LocalDateTime.of(after, LocalTime.MIN),
                    LocalDateTime.of(before, LocalTime.MAX));
        }
        if (after == null && before == null) {
            return workOrderRepository.findByUserUserId(userId);
        }
        return workOrderRepository.findByUserUserIdAndLastModificationTimeStampBetween(
                userId,
                LocalDateTime.of(after, LocalTime.MIN),
                LocalDateTime.of(before, LocalTime.MAX));
    }
    	public List<WorkOrder> getActiveWorkOrders(Long userId) {

	        if (userId == null) {
	            return workOrderRepository.findByIsActiveTrue();
	            }
	 
	        return workOrderRepository.findByUserUserIdAndIsActiveTrue(userId);
    }    
    
        public void assignUserToWorkOrder(Long userId, Long workOrderId) {
		
	    User user = userRepository.findById(userId).orElseThrow(null);
	    WorkOrder workOrder = workOrderRepository.findById(workOrderId).orElse(null);
	    
	    workOrder.setUser(user);
	    workOrder.setIsActive(true);
		workOrder.setStartTimeStamp(LocalDateTime.now());
		workOrder.setLastModificationTimeStamp(LocalDateTime.now());
	    
	    userRepository.save(user);
	    workOrderRepository.save(workOrder);
	  }
	
	public List<WorkOrder> getWorkOrdersByUser(Long userId) {
		 List<WorkOrder> workOrders = workOrderRepository.findByUserUserId(userId);
		 return workOrders;
	}

//	public List<WorkOrder> getActiveWorkOrdersByUser(Long userId) {
//		 User user = userRepository.findById(userId).orElse(null);
//		 List<WorkOrder> workOrders = workOrderRepository.findByUserUserId(userId);
//		 return workOrders;
//	}

	public WorkOrder completeWorkOrder(WorkOrder workOrder) {
		workOrder.setEndTimeStamp(LocalDateTime.now());
		workOrder.setLastModificationTimeStamp(LocalDateTime.now());
		workOrder.setCompleted(true);
		workOrder.setIsActive(false);
		workOrderRepository.save(workOrder);
//		payrollService.updatePayroll(workOrder);
		return workOrder;
	}
}
