package com.godel.employeemanagementrestful.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.godel.employeemanagementrestful.dto.MonthlyStatsDTO;
import com.godel.employeemanagementrestful.dto.PayrollStatisticsDTO;
import com.godel.employeemanagementrestful.dto.YearlyStatsDTO;
import com.godel.employeemanagementrestful.service.PayrollStatisticsService;

@RestController
@RequestMapping("/api/v1/statistics")
public class PayrollStatisticsController {
	
    @Autowired
    private PayrollStatisticsService payrollStatisticsService;


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
