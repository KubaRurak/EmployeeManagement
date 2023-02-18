package com.godel.employeemanagementrestful.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

import com.godel.employeemanagementrestful.entity.Payroll;
import com.godel.employeemanagementrestful.entity.User;

import lombok.Data;

@Data
public class PayrollDTO {
	
	private Long payrollId;
	private LocalDate payrollMonth;
	private BigDecimal hoursWorked;
	private BigDecimal moneyGenerated;
	private Long userId;
	private String userEmail;
	
	
	
	public PayrollDTO(Payroll payroll) {
		this.payrollId=payroll.getPayrollId();
		this.payrollMonth=payroll.getPayrollMonth();
		this.hoursWorked=payroll.getHoursWorked();
		this.moneyGenerated=payroll.getMoneyGenerated();

        User user = payroll.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.userEmail = user.getEmailId();
        }
	}
	
	

}
