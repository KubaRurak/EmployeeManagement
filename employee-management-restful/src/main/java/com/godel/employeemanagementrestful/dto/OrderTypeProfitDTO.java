package com.godel.employeemanagementrestful.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderTypeProfitDTO {
    private String orderTypeName;
    private BigDecimal totalProfit;

    public OrderTypeProfitDTO(String orderTypeName, BigDecimal totalProfit) {
        this.orderTypeName = orderTypeName;
        this.totalProfit = totalProfit;
    }

}
