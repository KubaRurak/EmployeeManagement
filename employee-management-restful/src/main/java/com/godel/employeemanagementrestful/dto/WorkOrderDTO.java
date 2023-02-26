package com.godel.employeemanagementrestful.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	private Boolean completed;
	private Boolean canceled;
	private	Boolean isActive;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTimeStamp;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endTimeStamp;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lastModificationTimeStamp;
	private String comments;
	private Long userId;
	private String assigneeEmail;

	public WorkOrderDTO(WorkOrder workOrder) {
		
		this.orderId = workOrder.getOrderId();
		this.orderName = workOrder.getOrderName();
		this.orderType = workOrder.getOrderType();
		this.status = workOrder.getStatus();
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
