package com.godel.employeemanagementrestful.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.Data;

@Data
public class MonthlyUserStats {
    private Long userId;
    private BigDecimal hoursWorked;
    private BigDecimal moneyMade;

    // Calculate money per hour for this user for the month
    public BigDecimal getMoneyPerHour() {
        if (hoursWorked.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return moneyMade.divide(hoursWorked, 2, RoundingMode.HALF_UP);
    }
}