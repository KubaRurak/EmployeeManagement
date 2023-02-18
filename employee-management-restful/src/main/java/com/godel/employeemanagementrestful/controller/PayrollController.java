package com.godel.employeemanagementrestful.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.godel.employeemanagementrestful.dto.PayrollDTO;
import com.godel.employeemanagementrestful.entity.Payroll;
import com.godel.employeemanagementrestful.service.PayrollService;

@RestController
@RequestMapping("/api/v1/payroll")
public class PayrollController {
	
	@Autowired
	PayrollService payrollService;
	
	@GetMapping("")
    public ResponseEntity<List<PayrollDTO>> getFilteredPayrolls(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate payrollMonth) {
        List<Payroll> payrolls = payrollService.getFilteredPayroll(userId, payrollMonth);
        List<PayrollDTO> payrollDTOS = payrolls.stream()
                .map(payroll -> new PayrollDTO(payroll))
                .collect(Collectors.toList());
        return ResponseEntity.ok(payrollDTOS);
	}
	
	

}
