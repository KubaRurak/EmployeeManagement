package com.godel.employeemanagementrestful.dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class MonthlyStatsDTO {
	
    private Map<Long, MonthlyUserStats> statsByUser;
    private Long userIdWithMostHours;
    private Long userIdWithMostMoney;
    private BigDecimal mostMoneyThisMonth;
    private BigDecimal mostHoursThisMonth;
    private BigDecimal bestMoneyPerHour;
    
    public MonthlyStatsDTO() {
        this.statsByUser = new HashMap<>();
    }

}