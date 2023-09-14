package com.godel.employeemanagementrestful.dto;

import lombok.Data;

@Data
public class PayrollStatisticsDTO {
    private MonthlyStatsDTO currentMonthStats; // Stats for the current month.
    private YearlyStatsDTO yearlyStats; // Stats for all months in the current year.
}