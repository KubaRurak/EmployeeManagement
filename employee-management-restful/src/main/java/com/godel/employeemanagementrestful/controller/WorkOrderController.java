package com.godel.employeemanagementrestful.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
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
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.enums.OrderStatus;
import com.godel.employeemanagementrestful.repository.WorkOrderRepository;
import com.godel.employeemanagementrestful.service.PayrollService;
import com.godel.employeemanagementrestful.service.WorkOrderService;

@RestController
@RequestMapping("/api/v1/workorders")
public class WorkOrderController {
	
	@Autowired
	private WorkOrderService workOrderService;
	
	@Autowired
	private WorkOrderRepository workOrderRepository;
		
	
	@GetMapping("")
    public ResponseEntity<List<WorkOrderDTO>> getFilteredWorkOrders(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate after,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate before) {
        List<WorkOrder> workOrders = workOrderService.getFilteredWorkOrders(userId, after, before);
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
	    workOrder.setOrderType(workOrderDTO.getOrderType());
	    workOrder.setStatus(OrderStatus.UNASSIGNED);
	    workOrder.setComments(workOrderDTO.getComments() != null ? 
                workOrderDTO.getComments() : null);
	    workOrder.setStartTimeStamp(null);
	    workOrder.setEndTimeStamp(null);
	    workOrder.setLastModificationTimeStamp(LocalDateTime.now());
	    workOrderRepository.save(workOrder);
	    if (workOrderDTO.getUserId() != null) {
	        assignUserToWorkOrder(workOrderDTO.getUserId(), workOrder.getOrderId());
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
	    workOrder.setOrderName(workOrderDTO.getOrderName());
	    workOrder.setOrderType(workOrderDTO.getOrderType());
	    workOrder.setStatus(workOrderDTO.getStatus());
	    workOrder.setStartTimeStamp(workOrderDTO.getStartTimeStamp());
	    workOrder.setEndTimeStamp(workOrderDTO.getEndTimeStamp());
	    workOrder.setLastModificationTimeStamp(LocalDateTime.now());
	    workOrder.setComments(workOrderDTO.getComments());
	    workOrderRepository.save(workOrder);
	    if (workOrderDTO.getUserId() != null) {
	        workOrderService.assignUserToWorkOrder(workOrderDTO.getUserId(), orderId);
	    }
	    if (workOrderDTO.getCustomerId() != null) {
	        workOrderService.assignCustomerToWorkOrder(workOrderDTO.getCustomerId(), orderId);
	    }
	    return new WorkOrderDTO(workOrder);	
	}

	@PutMapping("/{orderId}/complete")
	public WorkOrderDTO completeWorkOrder(@PathVariable Long orderId) {
		WorkOrder workOrder = workOrderRepository.findByOrderId(orderId);
		workOrderService.completeWorkOrder(workOrder);
		WorkOrderDTO workOrderDTO = new WorkOrderDTO(workOrder);
		return workOrderDTO;
	}

	
}
