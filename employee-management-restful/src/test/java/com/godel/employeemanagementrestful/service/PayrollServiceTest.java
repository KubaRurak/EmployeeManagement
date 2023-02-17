package com.godel.employeemanagementrestful.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PayrollServiceTest {
	@Autowired
	PayrollService payrollService;

	@Test
	void generatePayrollTest(){
		YearMonth thisYearMonth = YearMonth.of(2023, 2);
		payrollService.generatePayroll(thisYearMonth);
	}

}
