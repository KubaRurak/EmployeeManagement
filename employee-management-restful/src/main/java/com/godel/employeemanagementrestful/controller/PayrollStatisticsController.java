package com.godel.employeemanagementrestful.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.godel.employeemanagementrestful.dto.MonthlyEarningsDTO;
import com.godel.employeemanagementrestful.dto.MonthlyStatsDTO;
import com.godel.employeemanagementrestful.dto.PayrollStatisticsDTO;
import com.godel.employeemanagementrestful.dto.UserDTO;
import com.godel.employeemanagementrestful.dto.UserStatsDTO;
import com.godel.employeemanagementrestful.dto.YearlyStatsDTO;
import com.godel.employeemanagementrestful.service.PayrollStatisticsService;

@RestController
@RequestMapping("/api/v1/statistics")
public class PayrollStatisticsController {
	
    @Autowired
    private PayrollStatisticsService payrollStatisticsService;

    @GetMapping("/lastMonthProfit")
    public ResponseEntity<BigDecimal> getTotalProfitForLastMonth() {
        return ResponseEntity.ok(payrollStatisticsService.getTotalProfitForLastMonth());
    }
    
    @GetMapping("/totalProfit")
    public ResponseEntity<BigDecimal> getTotalProfit() {
        return ResponseEntity.ok(payrollStatisticsService.getTotalProfit());
    }
    
    @GetMapping("/top3EmployeesByHours/{year}/{month}")
    public ResponseEntity<List<UserStatsDTO>> getTop3EmployeesByHoursForMonth(@PathVariable int year, @PathVariable int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        List<UserStatsDTO> top3EmployeesByHours = payrollStatisticsService.findTop3EmployeesByHoursWorkedForMonth(date);
        
        return ResponseEntity.ok(top3EmployeesByHours);
    }

    @GetMapping("/top3EmployeesByMoney/{year}/{month}")
    public ResponseEntity<List<UserStatsDTO>> getTop3EmployeesByMoneyForMonth(@PathVariable int year, @PathVariable int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        List<UserStatsDTO> top3EmployeesByMoney = payrollStatisticsService.findTop3EmployeesByMoneyGeneratedForMonth(date);
        
        return ResponseEntity.ok(top3EmployeesByMoney);
    }
    
    @GetMapping("/monthly-earnings/last-two-years")
    public ResponseEntity<List<MonthlyEarningsDTO>> getMonthlyEarningsForLastTwoYears() {
        List<MonthlyEarningsDTO> monthlyEarnings = payrollStatisticsService.getMonthlyEarningsForLastTwoYears();
        return ResponseEntity.ok(monthlyEarnings);
    }


    @GetMapping("/yearly/{year}")
    public ResponseEntity<YearlyStatsDTO> getYearlyStatistics(@PathVariable int year) {
        YearlyStatsDTO yearlyStatsDTO = payrollStatisticsService.computeYearlyStats(year);
        return ResponseEntity.ok(yearlyStatsDTO);
    }

    @GetMapping("/monthly/{year}/{month}")
    public ResponseEntity<MonthlyStatsDTO> getMonthlyStatistics(@PathVariable int year, @PathVariable int month) {
        MonthlyStatsDTO monthlyStatsDTO = payrollStatisticsService.computeMonthlyStats(LocalDate.of(year, month, 1));
        return ResponseEntity.ok(monthlyStatsDTO);
    }
}
