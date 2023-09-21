package com.godel.employeemanagementrestful.dto;

import lombok.Data;

@Data
public class MonthlyEarningsDTO {
    private double totalMoney;
    private int year;
    private int month;
    
    public MonthlyEarningsDTO(double totalMoney, int year, int month) {
        this.totalMoney = totalMoney;
        this.year = year;
        this.month = month;
    }

}