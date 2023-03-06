package com.godel.employeemanagementrestful.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godel.employeemanagementrestful.dto.WorkOrderDTO;
import com.godel.employeemanagementrestful.entity.Customer;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.enums.OrderStatus;
import com.godel.employeemanagementrestful.mapper.WorkOrderMapperImpl;
import com.godel.employeemanagementrestful.repository.CustomerRepository;
import com.godel.employeemanagementrestful.repository.UserRepository;
import com.godel.employeemanagementrestful.repository.WorkOrderRepository;

@Service
public class WorkOrderService{

	@Autowired
	private WorkOrderRepository workOrderRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PayrollService payrollService;
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
	
	public List<WorkOrder> getActiveWorkOrders(Long userId) {
		if (userId == null) {
			return workOrderRepository.findByStatus(OrderStatus.ACTIVE);
		}
		return workOrderRepository.findByUserUserIdAndStatus(userId, OrderStatus.ACTIVE);
	}    

	public void assignCustomerToWorkOrder(Long customerId, Long workOrderId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(null);
		WorkOrder workOrder = workOrderRepository.findById(workOrderId).orElse(null);
		workOrder.setCustomer(customer);
		workOrder.setLastModificationTimeStamp(LocalDateTime.now());
		customerRepository.save(customer);
		workOrderRepository.save(workOrder);
	}
	
	public void assignUserToWorkOrder(Long userId, Long workOrderId) {
		User user = userRepository.findById(userId).orElseThrow(null);
		WorkOrder workOrder = workOrderRepository.findById(workOrderId).orElse(null);
		workOrder.setUser(user);
		workOrder.setStatus(OrderStatus.ACTIVE);
		workOrder.setStartTimeStamp(LocalDateTime.now());
		workOrder.setLastModificationTimeStamp(LocalDateTime.now());
		userRepository.save(user);
		workOrderRepository.save(workOrder);
	}

	public List<WorkOrder> getWorkOrdersByUser(Long userId) {
		List<WorkOrder> workOrders = workOrderRepository.findByUserUserId(userId);
		return workOrders;
	}

	public WorkOrder completeWorkOrder(WorkOrder workOrder) {
		workOrder.setEndTimeStamp(LocalDateTime.now());
		workOrder.setLastModificationTimeStamp(LocalDateTime.now());
		workOrder.setStatus(OrderStatus.COMPLETED);
		payrollService.updatePayrollMoney(workOrder);
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
}
