package com.godel.employeemanagementrestful.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;

import lombok.Data;

@Data
public class UserDTO {

	private Long userId;
	private String emailId;
	private String firstName;
	private String lastName;
	private List<Long> workOrderIds;
	
	public UserDTO(User user) {
        this.userId = user.getUserId();
        this.emailId = user.getEmailId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.workOrderIds = user.getWorkOrders()
                .stream()
                .map(WorkOrder::getOrderId)
                .collect(Collectors.toList());
	}

	public UserDTO() {
		super();
	}


}
