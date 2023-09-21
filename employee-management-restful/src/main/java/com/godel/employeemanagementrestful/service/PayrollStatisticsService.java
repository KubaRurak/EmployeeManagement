package com.godel.employeemanagementrestful.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.godel.employeemanagementrestful.dto.MonthlyEarningsDTO;
import com.godel.employeemanagementrestful.dto.MonthlyStatsDTO;
import com.godel.employeemanagementrestful.dto.MonthlyUserStats;
import com.godel.employeemanagementrestful.dto.UserStatsDTO;
import com.godel.employeemanagementrestful.dto.YearlyStatsDTO;
import com.godel.employeemanagementrestful.entity.Payroll;
import com.godel.employeemanagementrestful.repository.PayrollRepository;

@Service
public class PayrollStatisticsService {
	
	@Autowired
	PayrollRepository payrollRepository;
	
	public List<UserStatsDTO> findTop3EmployeesByHoursWorkedForMonth(LocalDate month) {
	    Pageable topThreeByHours = PageRequest.of(0, 3, Sort.by(Sort.Order.desc("timeWorked")));
	    
	    List<Payroll> topPayrollsByHours = payrollRepository.findByPayrollMonth(month, topThreeByHours);
	    
	    return topPayrollsByHours.stream()
	            .map(payroll -> new UserStatsDTO(payroll.getUser(), payroll.getTimeWorked(), payroll.getMoneyGenerated()))
	            .collect(Collectors.toList());
	}

	public List<UserStatsDTO> findTop3EmployeesByMoneyGeneratedForMonth(LocalDate month) {
	    Pageable topThreeByMoney = PageRequest.of(0, 3, Sort.by(Sort.Order.desc("moneyGenerated")));
	    
	    List<Payroll> topPayrollsByMoney = payrollRepository.findByPayrollMonth(month, topThreeByMoney);
	    
	    return topPayrollsByMoney.stream()
	            .map(payroll -> new UserStatsDTO(payroll.getUser(), payroll.getTimeWorked(), payroll.getMoneyGenerated()))
	            .collect(Collectors.toList());
	}
    
    public List<MonthlyEarningsDTO> getMonthlyEarningsForLastTwoYears() {
        int currentYear = LocalDate.now().getYear();
        List<Object[]> results = payrollRepository.findMonthlyEarningsForYears(currentYear, currentYear - 1);
        
        return results.stream()
            .map(record -> new MonthlyEarningsDTO(
                ((Number) record[0]).doubleValue(), 
                ((Number) record[1]).intValue(), 
                ((Number) record[2]).intValue()))
            .collect(Collectors.toList());
    }
    
    
    
    
    
    
	
    
    public YearlyStatsDTO computeYearlyStats(int year) {
        YearlyStatsDTO yearlyStats = new YearlyStatsDTO();

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

