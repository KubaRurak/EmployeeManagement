package com.godel.employeemanagementrestful.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	@NotNull
	private User user;
	@NotNull
	private LocalDate payrollMonth;
	
	@PositiveOrZero
	private BigDecimal timeWorked;
	
	@PositiveOrZero
	private BigDecimal moneyGenerated;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(
			name="payroll_id",
			referencedColumnName = "payrollId"
			)
	private List<Timetable> timetables;
	  
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(
			name="payroll_id",
			referencedColumnName = "payrollId"
			)
	private List<WorkOrder> workOrders;

	public Payroll(User user, LocalDate payrollMonth, BigDecimal timeWorked, BigDecimal moneyGenerated) {
		super();
		this.user = user;
		this.payrollMonth = payrollMonth;
		this.timeWorked = timeWorked;
		this.moneyGenerated = moneyGenerated;
	}
	
	public void addAmount(BigDecimal amount) {
		this.moneyGenerated = this.moneyGenerated.add(amount);
	}
	
	public void addTimeWorked(BigDecimal hours) {
		this.timeWorked = this.timeWorked.add(hours);
	}	
}

