package com.godel.employeemanagementrestful.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import com.godel.employeemanagementrestful.entity.Payroll;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.enums.Role;

import lombok.Data;

@Data
public class PayrollDTO {
	
	private Long payrollId;
	private LocalDate payrollMonth;
	private BigDecimal timeWorked;
	private BigDecimal moneyGenerated;
	private Long userId;
	private String userEmail;
	private String userFirstName;
	private String userLastName;
	private Role userRole;
	private List<Long> workOrderIds;
	
	
	
	public PayrollDTO(Payroll payroll) {
		this.payrollId=payroll.getPayrollId();
		this.payrollMonth=payroll.getPayrollMonth();
		this.timeWorked=payroll.getTimeWorked();
		this.moneyGenerated=payroll.getMoneyGenerated();

        User user = payroll.getUser();
        if (user != null) {
            this.userId = user.getUserId();
            this.userEmail = user.getEmailId();
            this.userFirstName = user.getFirstName();
            this.userLastName = user.getLastName();
            this.userRole = user.getRole();
        }
        
        List<Long> workOrderIds = payroll.getWorkOrders()
        		.stream()
        		.map(WorkOrder::getOrderId)
        		.collect(Collectors.toList());
        
        this.workOrderIds = workOrderIds;

	}
	
	

}
