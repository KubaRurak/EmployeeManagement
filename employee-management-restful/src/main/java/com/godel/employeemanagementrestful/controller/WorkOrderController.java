package com.godel.employeemanagementrestful.controller;


import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.godel.employeemanagementrestful.dto.WorkOrderDTO;
import com.godel.employeemanagementrestful.entity.OrderType;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.enums.OrderStatus;
import com.godel.employeemanagementrestful.repository.OrderTypeRepository;
import com.godel.employeemanagementrestful.repository.UserRepository;
import com.godel.employeemanagementrestful.repository.WorkOrderRepository;
import com.godel.employeemanagementrestful.service.PayrollService;
import com.godel.employeemanagementrestful.service.WorkOrderService;

@RestController
@RequestMapping("/api/v1/workorders")
public class WorkOrderController {
	
	private static final Logger log = LoggerFactory.getLogger(WorkOrderController.class);
	
	@Autowired
	private WorkOrderService workOrderService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WorkOrderRepository workOrderRepository;
	
	@Autowired
	private OrderTypeRepository orderTypeRepository;
		
	@GetMapping("/amount")
	public ResponseEntity<Long> getWorkOrderAmount(@RequestParam(required = false) OrderStatus status) {
	    Long amountOfWorkOrders;
	    System.out.println("Received orderStatus: " + status);

	    if (status != null) {
	        amountOfWorkOrders = workOrderRepository.countByStatus(status);
	    } else {
	        amountOfWorkOrders = workOrderRepository.count();
	    }
	    
	    return ResponseEntity.ok(amountOfWorkOrders);
	}
	
	
	@GetMapping("")
	public ResponseEntity<List<WorkOrderDTO>> getFilteredWorkOrders(
	        @RequestParam(required = false) Long userId,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate after,
	        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate before,
	        @RequestParam(required = false) OrderStatus status) {
	    
	    List<WorkOrder> workOrders;
	    
	    if(status != null) {
	        workOrders = workOrderService.getWorkOrdersByStatus(userId, after, before, status);
	    } else {
	        workOrders = workOrderService.getFilteredWorkOrders(userId, after, before);
	    }

	    List<WorkOrderDTO> workOrderDTOS = workOrders.stream()
	            .map(workOrder -> new WorkOrderDTO(workOrder))
	            .collect(Collectors.toList());
	    return ResponseEntity.ok(workOrderDTOS);
	}
	
	@GetMapping("/active")
    public ResponseEntity<List<WorkOrderDTO>> getActiveWorkOrders(
            @RequestParam(required = false) Long userId){
        List<WorkOrder> workOrders = workOrderService.getActiveWorkOrders(userId);
        List<WorkOrderDTO> workOrderDTOS = workOrders.stream()
                .map(workOrder -> new WorkOrderDTO(workOrder))
                .collect(Collectors.toList());
        return ResponseEntity.ok(workOrderDTOS);
	}
	
	@GetMapping("/{orderId}")
	public WorkOrderDTO fetchWorkOrderById(@PathVariable Long orderId) {
		WorkOrder workOrder = workOrderRepository.findByOrderId(orderId);
		WorkOrderDTO workOrderDTO = new WorkOrderDTO(workOrder);
		return workOrderDTO;
	}
	
	@GetMapping("/batch")
	public ResponseEntity<List<WorkOrderDTO>> fetchWorkOrdersByIds(@RequestParam List<Long> orderIds) {
	    log.info("Received orderIds: " + orderIds);
	    List<WorkOrder> workOrders = workOrderRepository.findAllByOrderIdIn(orderIds);
	    List<WorkOrderDTO> workOrderDTOs = workOrders.stream()
	            .map(WorkOrderDTO::new)
	            .collect(Collectors.toList());
	    return ResponseEntity.ok(workOrderDTOs);
	}
	
	@PutMapping("/assign")
	public ResponseEntity<?> assignUserToWorkOrder(
			@RequestParam(required = true) Long userId,
			@RequestParam(required = true) Long orderId) {
		
		workOrderService.assignUserToWorkOrder(userId, orderId);
		
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{orderId}")
	public ResponseEntity<Void> deleteWorkOderById(@PathVariable Long orderId) {
		workOrderRepository.deleteById(orderId);
		return ResponseEntity.noContent().build();
	}

	
	@PostMapping("")
	public WorkOrderDTO createWorkOrder(@RequestBody WorkOrderDTO workOrderDTO) {
	    WorkOrder workOrder = new WorkOrder();
	    workOrder.setOrderName(workOrderDTO.getOrderName());
	    OrderType orderType = orderTypeRepository.findById(workOrderDTO.getOrderType().getId())
	            .orElseThrow(() -> new RuntimeException("OrderType not found"));
	    workOrder.setOrderType(orderType);
	    workOrder.setStatus(OrderStatus.UNASSIGNED);
	    workOrder.setComments(workOrderDTO.getComments() != null ? 
                workOrderDTO.getComments() : null);
	    workOrder.setStartTimeStamp(null);
	    workOrder.setEndTimeStamp(null);
	    workOrder.setLastModificationTimeStamp(LocalDateTime.now());
	    workOrderRepository.save(workOrder);
	    if (workOrderDTO.getUserId() != null) {
	        workOrderService.assignUserToWorkOrder(workOrderDTO.getUserId(), workOrder.getOrderId());
	    }
	    if (workOrderDTO.getCustomerId() != null) {
	        workOrderService.assignCustomerToWorkOrder(workOrderDTO.getCustomerId(), workOrder.getOrderId());
	    }
	    return new WorkOrderDTO(workOrder);
	}
	
	@PutMapping("/{orderId}")
	public WorkOrderDTO updateWorkOrderById(
	        @PathVariable Long orderId,
	        @RequestBody WorkOrderDTO workOrderDTO) {
	    WorkOrder workOrder = workOrderRepository.findById(orderId).orElseThrow();
	    OrderType orderType = orderTypeRepository.findById(workOrderDTO.getOrderType().getId()).get();
	    boolean wasUnassigned = OrderStatus.UNASSIGNED.equals(workOrder.getStatus());
	    if (workOrderDTO.getUserId() != null) {
	        workOrderService.assignUserToWorkOrder(workOrderDTO.getUserId(), orderId);
	    }
	    if (workOrderDTO.getCustomerId() != null) {
	        workOrderService.assignCustomerToWorkOrder(workOrderDTO.getCustomerId(), orderId);
	    }
	    workOrder.setOrderName(workOrderDTO.getOrderName());
	    workOrder.setOrderType(orderType);
	    workOrder.setStatus(workOrderDTO.getStatus());
	    if (wasUnassigned && workOrderDTO.getUserId() != null) {
	        workOrder.setStartTimeStamp(LocalDateTime.now());
	    } else {
	        workOrder.setStartTimeStamp(workOrderDTO.getStartTimeStamp());
	    }
	    workOrder.setEndTimeStamp(workOrderDTO.getEndTimeStamp());
	    workOrder.setLastModificationTimeStamp(LocalDateTime.now());
	    workOrder.setComments(workOrderDTO.getComments());
	    workOrderRepository.save(workOrder);
	    orderTypeRepository.save(orderType);
	    return new WorkOrderDTO(workOrder);    
	}
	
	@PreAuthorize("hasAnyAuthority('ENGINEER', 'OPERATOR', 'ADMIN')")
	@PutMapping("/{orderId}/complete")
	public ResponseEntity<String> completeWorkOrder(@PathVariable Long orderId) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String authenticatedEmail = authentication.getName();
	    User user = userRepository.findByEmailId(authenticatedEmail).orElse(null);
		WorkOrder workOrder = workOrderRepository.findByOrderId(orderId);
		long userId = workOrder.getUser().getUserId();
	    if (user == null || !user.getUserId().equals(userId)) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You complete workorder for another user.");
	    }
		workOrderService.completeWorkOrder(workOrder);
		return ResponseEntity.ok("Work order completed succesfully");
	}
	
	@GetMapping("/recent")
	public ResponseEntity<List<WorkOrderDTO>> getRecentWorkOrders() {
	    List<WorkOrder> workOrders = workOrderService.getRecentWorkOrders();
	    List<WorkOrderDTO> workOrderDTOs = workOrders.stream()
	            .map(WorkOrderDTO::new)
	            .collect(Collectors.toList());
	    return ResponseEntity.ok(workOrderDTOs);
	}

	
}
