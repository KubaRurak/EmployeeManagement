package com.godel.employeemanagementrestful.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godel.employeemanagementrestful.dto.MonthlyStatsDTO;
import com.godel.employeemanagementrestful.dto.MonthlyUserStats;
import com.godel.employeemanagementrestful.dto.YearlyStatsDTO;
import com.godel.employeemanagementrestful.repository.PayrollRepository;

@Service
public class PayrollStatisticsService {
	
	@Autowired
	PayrollRepository payrollRepository;
	
	
    
    public YearlyStatsDTO computeYearlyStats(int year) {
        YearlyStatsDTO yearlyStats = new YearlyStatsDTO();

        // 1. Populate basic yearly stats
        Map<Long, BigDecimal> yearlyHours = convertToMap(payrollRepository.findYearlyHoursWorkedByUsers(year));
        Map<Long, BigDecimal> yearlyMoney = convertToMap(payrollRepository.findYearlyMoneyMadeByUsers(year));

        yearlyStats.setYearlyHoursWorkedByUser(yearlyHours);
        yearlyStats.setYearlyMoneyMadeByUser(yearlyMoney);
        yearlyStats.setYearlyUserIdWithMostHours(findUserIdWithMostHours(yearlyHours));
        yearlyStats.setYearlyUserIdWithMostMoney(findUserIdWithMostMoney(yearlyMoney));
        yearlyStats.setYearlyBestMoneyPerHour(calculateBestMoneyPerHour(yearlyMoney, yearlyHours));

        // 2. Compute monthly breakdown for the year and populate it
        for (int month = 1; month <= 12; month++) {
            LocalDate monthDate = LocalDate.of(year, month, 1);
            MonthlyStatsDTO statsForMonth = computeMonthlyStats(monthDate);
            yearlyStats.getMonthlyStats().put(monthDate, statsForMonth);
        }

        return yearlyStats;
    }

    public MonthlyStatsDTO computeMonthlyStats(LocalDate monthDate) {
        MonthlyStatsDTO stats = new MonthlyStatsDTO();

        // Retrieve and convert data
        Map<Long, BigDecimal> monthlyHours = convertToMap(payrollRepository.findMonthlyHoursWorkedByUsers(monthDate));
        Map<Long, BigDecimal> monthlyMoney = convertToMap(payrollRepository.findMonthlyMoneyMadeByUsers(monthDate));

        // Populate MonthlyUserStats
        for (Long userId : monthlyHours.keySet()) {
            MonthlyUserStats userStats = new MonthlyUserStats();
            userStats.setUserId(userId);
            userStats.setHoursWorked(monthlyHours.get(userId));
            userStats.setMoneyMade(monthlyMoney.get(userId));
            stats.getStatsByUser().put(userId, userStats);
        }

        stats.setUserIdWithMostHours(findUserIdWithMostHours(monthlyHours));
        stats.setUserIdWithMostMoney(findUserIdWithMostMoney(monthlyMoney));
        stats.setBestMoneyPerHour(calculateBestMoneyPerHour(monthlyMoney, monthlyHours));
        
        return stats;
    }

    private Long findUserIdWithMostHours(Map<Long, BigDecimal> hoursWorkedByUser) {
        return hoursWorkedByUser.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    private Long findUserIdWithMostMoney(Map<Long, BigDecimal> moneyMadeByUser) {
        return moneyMadeByUser.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    private BigDecimal calculateBestMoneyPerHour(Map<Long, BigDecimal> moneyMade, Map<Long, BigDecimal> hoursWorked) {
        BigDecimal bestMoneyPerHour = BigDecimal.ZERO;
        
        for (Map.Entry<Long, BigDecimal> entry : moneyMade.entrySet()) {
            BigDecimal money = entry.getValue();
            BigDecimal hours = hoursWorked.get(entry.getKey());

            if (hours.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal ratio = money.divide(hours, RoundingMode.HALF_UP);
                bestMoneyPerHour = bestMoneyPerHour.max(ratio);
            }
        }

        return bestMoneyPerHour;
    }
    
    private Map<Long, BigDecimal> convertToMap(List<Object[]> results) {
        return results.stream()
            .collect(Collectors.toMap(
                row -> (Long) row[0],
                row -> (BigDecimal) row[1]
            ));
    }
}

