package com.godel.employeemanagementrestful.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godel.employeemanagementrestful.dto.OrderTypeProfitDTO;
import com.godel.employeemanagementrestful.dto.WorkOrderDTO;
import com.godel.employeemanagementrestful.entity.Customer;
import com.godel.employeemanagementrestful.entity.Payroll;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.enums.OrderStatus;
import com.godel.employeemanagementrestful.mapper.WorkOrderMapperImpl;
import com.godel.employeemanagementrestful.repository.CustomerRepository;
import com.godel.employeemanagementrestful.repository.PayrollRepository;
import com.godel.employeemanagementrestful.repository.UserRepository;
import com.godel.employeemanagementrestful.repository.WorkOrderRepository;

import jakarta.transaction.Transactional;

@Service
public class WorkOrderService{

	@Autowired
	private WorkOrderRepository workOrderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PayrollRepository payrollRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private WorkOrderMapperImpl workOrderMapper;

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
	
	public List<WorkOrder> getWorkOrdersByStatus(Long userId, LocalDate after, LocalDate before, OrderStatus status) {
	    if (userId == null && after == null && before == null) {
	        return workOrderRepository.findByStatus(status);
	    }
	    if (userId == null) {
	        return workOrderRepository.findByStatusAndLastModificationTimeStampBetween(
	                status,
	                LocalDateTime.of(after, LocalTime.MIN),
	                LocalDateTime.of(before, LocalTime.MAX));
	    }
	    if (after == null && before == null) {
	        return workOrderRepository.findByUserUserIdAndStatus(userId, status);
	    }
	    return workOrderRepository.findByUserUserIdAndStatusAndLastModificationTimeStampBetween(
	            userId,
	            status,
	            LocalDateTime.of(after, LocalTime.MIN),
	            LocalDateTime.of(before, LocalTime.MAX));
	}
	
	public List<WorkOrder> getActiveWorkOrders(Long userId) {
		if (userId == null) {
			return workOrderRepository.findByStatus(OrderStatus.ACTIVE);
		}
		return workOrderRepository.findByUserUserIdAndStatus(userId, OrderStatus.ACTIVE);
	}    
	@Transactional
	public void assignCustomerToWorkOrder(Long customerId, Long workOrderId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(null);
		WorkOrder workOrder = workOrderRepository.findById(workOrderId).orElse(null);
		workOrder.setCustomer(customer);
		workOrder.setLastModificationTimeStamp(LocalDateTime.now());
		workOrderRepository.save(workOrder);
	}
	@Transactional
	public void assignUserToWorkOrder(Long userId, Long workOrderId) {
		User user = userRepository.findById(userId).orElseThrow(null);
		WorkOrder workOrder = workOrderRepository.findById(workOrderId).orElse(null);
		workOrder.setUser(user);
		workOrder.setStartTimeStamp(LocalDateTime.now());
		workOrder.setLastModificationTimeStamp(LocalDateTime.now());
		workOrder.setStatus(OrderStatus.ACTIVE);
		workOrderRepository.save(workOrder);
	}

	public List<WorkOrder> getWorkOrdersByUser(Long userId) {
		List<WorkOrder> workOrders = workOrderRepository.findByUserUserId(userId);
		return workOrders;
	}
	@Transactional
	public WorkOrder completeWorkOrder(WorkOrder workOrder) {
	    LocalDate payrollMonth = LocalDateTime.now().toLocalDate().withDayOfMonth(1); 
	    Payroll payroll = payrollRepository.findByUserAndPayrollMonth(workOrder.getUser(), payrollMonth);
//	    if (payroll == null) {
//	        throw new IllegalStateException("Expected Payroll not found for user " + workOrder.getUser().getUserId() + " and month " + payrollMonth);
//	    }
	    workOrder.setPayroll(payroll);
	    workOrder.complete();
	    workOrderRepository.save(workOrder);
	    return workOrder;
	}
	
    public WorkOrderDTO createWorkOrder(WorkOrderDTO workOrderDTO) {
        WorkOrder workOrder = workOrderMapper.mapperDtoToEntity(workOrderDTO);
        workOrder.setStatus(OrderStatus.UNASSIGNED);
        workOrder.setLastModificationTimeStamp(LocalDateTime.now());
        workOrderRepository.save(workOrder);
        return workOrderMapper.mapperEntityToDto(workOrder);
    }
    
    public List<WorkOrder> getRecentWorkOrders() {
        return workOrderRepository.findTop4ByOrderByLastModificationTimeStampDesc();
    }
    
    public List<OrderTypeProfitDTO> getProfitPerOrderType() {
        return workOrderRepository.findProfitPerOrderType();
    }
    

    
    
}
