package com.godel.employeemanagementrestful.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.enums.Role;

import lombok.Data;

@Data
public class UserStatsDTO {

	private Long userId;
	private String emailId;
	private String firstName;
	private String lastName;
	private Role role;
    private BigDecimal hoursWorked;
    private BigDecimal moneyGenerated;
	
    public UserStatsDTO(User user, BigDecimal hoursWorked, BigDecimal moneyGenerated) {
        this.userId = user.getUserId();
        this.emailId = user.getEmailId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
        this.hoursWorked = hoursWorked;
        this.moneyGenerated = moneyGenerated; 
    }

	public UserStatsDTO() {
		super();
	}


}
