package com.godel.employeemanagementrestful.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
		name = "tbl_payroll"			
)
public class Payroll {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long payrollId;
	
	@ManyToOne(
			cascade = CascadeType.ALL)
	@JoinColumn(
			name="user_id",
			referencedColumnName = "userId"
			)
	private User user;
	
	private LocalDate currentMonth;
	  
	private BigDecimal hoursWorked;
	  
	private BigDecimal moneyGenerated;

	public Payroll(User user, LocalDate currentMonth, BigDecimal hoursWorked, BigDecimal moneyGenerated) {
		super();
		this.user = user;
		this.currentMonth = currentMonth;
		this.hoursWorked = hoursWorked;
		this.moneyGenerated = moneyGenerated;
	}
	
	public void addAmount(BigDecimal amount) {
		this.moneyGenerated = this.moneyGenerated.add(amount);
	}
	
}

