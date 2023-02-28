package com.godel.employeemanagementrestful.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.godel.employeemanagementrestful.entity.OrderType;
import com.godel.employeemanagementrestful.entity.WorkOrder;

@SpringBootTest
class OrderTypeRepositoryTest {
	
	@Autowired
	OrderTypeRepository orderTypeRepository;

	@Test
	void createOrderType() {
		OrderType orderType = OrderType.builder()
				.orderTypeName("DTF")
				.price(new BigDecimal(2000))
				.build();
		
		orderTypeRepository.save(orderType);
	}

}
