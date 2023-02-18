package com.godel.employeemanagementrestful.repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.godel.employeemanagementrestful.entity.Payroll;
import com.godel.employeemanagementrestful.entity.Timetable;
import com.godel.employeemanagementrestful.entity.User;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

	Payroll findByUser(User user);


	List<Payroll> findByUserUserId(Long userId);

	List<Payroll> findByUserUserIdAndPayrollMonth(Long userId, LocalDate payrollMonth);

	List<Payroll> findByPayrollMonth(LocalDate payrollMonth);


	Payroll findByUserAndPayrollMonth(User user, LocalDate localDate);
	
}
