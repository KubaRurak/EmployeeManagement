package com.godel.employeemanagementrestful.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

import com.godel.employeemanagementrestful.entity.Payroll;
import com.godel.employeemanagementrestful.entity.User;

import lombok.Data;

@Data
public class PayrollDTO {
	
	private Long payrollId;
	private YearMonth yearMonth;
	private BigDecimal hoursWorked;
	private BigDecimal moneyGenerated;
	private Long userId;
	private String assigneeEmail;
	public PayrollDTO(Payroll payroll) {

        User user = payroll.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.assigneeEmail = user.getEmailId();
        }
	}
	
	

}
