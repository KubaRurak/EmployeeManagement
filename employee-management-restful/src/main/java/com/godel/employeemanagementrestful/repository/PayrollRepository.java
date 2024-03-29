package com.godel.employeemanagementrestful.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.godel.employeemanagementrestful.entity.Payroll;
import com.godel.employeemanagementrestful.entity.User;

public interface PayrollRepository extends JpaRepository<Payroll, Long> {

	Payroll findByUser(User user);

	List<Payroll> findByUserUserId(Long userId);

	List<Payroll> findByUserUserIdAndPayrollMonth(Long userId, LocalDate payrollMonth);

	List<Payroll> findByPayrollMonth(LocalDate payrollMonth);

	Payroll findByUserAndPayrollMonth(User user, LocalDate localDate);

	List<Payroll> findAllByPayrollMonth(LocalDate monthDate);
	
	@Query("SELECT p.user.userId, SUM(p.timeWorked) FROM Payroll p WHERE p.payrollMonth = :month GROUP BY p.user.userId")
	List<Object[]> findMonthlyHoursWorkedByUsers(@Param("month") LocalDate month);

	@Query("SELECT p.user.userId, SUM(p.moneyGenerated) FROM Payroll p WHERE p.payrollMonth = :month GROUP BY p.user.userId")
	List<Object[]> findMonthlyMoneyMadeByUsers(@Param("month") LocalDate month);

	@Query("SELECT p.user.id AS userId, SUM(p.timeWorked) AS totalHoursWorked " +
	       "FROM Payroll p " +
	       "WHERE YEAR(p.payrollMonth) = :year " +
	       "GROUP BY p.user.id")	
	List<Object[]> findYearlyHoursWorkedByUsers(@Param("year") int year);

	@Query("SELECT p.user.id AS userId, SUM(p.moneyGenerated) AS totalMoneyMade " +
	       "FROM Payroll p " +
	       "WHERE YEAR(p.payrollMonth) = :year " +
	       "GROUP BY p.user.id")
	List<Object[]> findYearlyMoneyMadeByUsers(@Param("year") int year);


	List<Payroll> findByPayrollMonth(LocalDate month, Pageable topThreeByHours);

    @Query("SELECT SUM(p.moneyGenerated) as totalMoney, YEAR(p.payrollMonth) as year, MONTH(p.payrollMonth) as month " +
            "FROM Payroll p " +
            "WHERE YEAR(p.payrollMonth) IN ?1 " +
            "GROUP BY YEAR(p.payrollMonth), MONTH(p.payrollMonth) " +
            "ORDER BY YEAR(p.payrollMonth), MONTH(p.payrollMonth)")
     List<Object[]> findMonthlyEarningsForYears(Integer... years);
     
     @Query("SELECT SUM(p.moneyGenerated) FROM Payroll p")
     BigDecimal findTotalProfits();
     
     @Query("SELECT SUM(p.moneyGenerated) FROM Payroll p WHERE p.payrollMonth = ?1")
     BigDecimal findTotalProfitForMonth(LocalDate month);
	
}
