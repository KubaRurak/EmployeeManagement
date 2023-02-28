package com.godel.employeemanagementrestful.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.godel.employeemanagementrestful.entity.Customer;
import com.godel.employeemanagementrestful.entity.OrderType;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.enums.OrderStatus;

import lombok.Data;

@Data
public class WorkOrderDTO {

	private Long orderId;
	private String orderName;
	private OrderType orderType;
	private OrderStatus status;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTimeStamp;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endTimeStamp;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastModificationTimeStamp;
	private String comments;
	
	private Long userId;
	private String assigneeEmail;
	private String userFirstName;
	private String userLastName;
	
	private Long customerId;
	private String customerEmail;
	private String customerFirstName;
	private String customerLastName;

	public WorkOrderDTO(WorkOrder workOrder) {
		
		this.orderId = workOrder.getOrderId();
		this.orderName = workOrder.getOrderName();
		this.orderType = workOrder.getOrderType();
		this.status = workOrder.getStatus();
		this.startTimeStamp = workOrder.getStartTimeStamp();
		this.endTimeStamp = workOrder.getEndTimeStamp();
		this.lastModificationTimeStamp = workOrder.getLastModificationTimeStamp();
		this.comments = workOrder.getComments();
		
        User user = workOrder.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.assigneeEmail = user.getEmailId();
            this.userFirstName = user.getFirstName();
            this.userLastName = user.getLastName();
        }
        
        Customer customer = workOrder.getCustomer();
        if (customer != null) {
            this.customerId = customer.getCustomerId();
            this.customerEmail = customer.getEmailId();
            this.customerFirstName = customer.getFirstName();
            this.customerLastName = customer.getLastName();
        }
	}

	public WorkOrderDTO() {
		super();
	}
}
