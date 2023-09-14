package com.godel.employeemanagementrestful.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.TreeMap;

import lombok.Data;

@Data
public class UserYearlyStatsDTO {
    private Long userId;
    private BigDecimal totalHoursWorked = BigDecimal.ZERO;;
    private BigDecimal totalMoneyMade = BigDecimal.ZERO;;
    private TreeMap<LocalDate, MonthlyUserStats> monthlyStats = new TreeMap<>();
}