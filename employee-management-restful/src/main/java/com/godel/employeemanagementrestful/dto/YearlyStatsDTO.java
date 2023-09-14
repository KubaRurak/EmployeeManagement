package com.godel.employeemanagementrestful.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import lombok.Data;

@Data
public class YearlyStatsDTO {

    // Aggregated yearly statistics
    private Map<Long, BigDecimal> yearlyHoursWorkedByUser;
    private Map<Long, BigDecimal> yearlyMoneyMadeByUser;
    private Long yearlyUserIdWithMostHours;
    private Long yearlyUserIdWithMostMoney;
    private BigDecimal yearlyBestMoneyPerHour;

    // Monthly breakdown for the entire year
    private TreeMap<LocalDate, MonthlyStatsDTO> monthlyStats = new TreeMap<>();
}
