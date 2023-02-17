package com.godel.employeemanagementrestful.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;

import lombok.Data;

@Data
public class WorkOrderDTO {

	private Long orderId;
	private String orderName;
	private String orderType;
	private BigDecimal price;
	private Boolean completed;
	private Boolean canceled;
	private	Boolean isActive;
	private LocalDateTime startTimeStamp;
	private LocalDateTime endTimeStamp;
	private LocalDateTime lastModificationTimeStamp;
	private String comments;
	private Long userId;
	private String assigneeEmail;

	public WorkOrderDTO(WorkOrder workOrder) {
		
		this.orderId = workOrder.getOrderId();
		this.orderName = workOrder.getOrderName();
		this.orderType = workOrder.getOrderType();
		this.price = workOrder.getPrice();
		this.completed = workOrder.getCompleted();
		this.canceled = workOrder.getCanceled();
		this.isActive = workOrder.getIsActive();
		this.startTimeStamp = workOrder.getStartTimeStamp();
		this.endTimeStamp = workOrder.getEndTimeStamp();
		this.lastModificationTimeStamp = workOrder.getLastModificationTimeStamp();
		this.comments = workOrder.getComments();
		
        User user = workOrder.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.assigneeEmail = user.getEmailId();
        }
	}

	public WorkOrderDTO() {
		super();
	}
}
