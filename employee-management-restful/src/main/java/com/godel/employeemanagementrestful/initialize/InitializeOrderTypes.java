package com.godel.employeemanagementrestful.initialize;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.godel.employeemanagementrestful.entity.OrderType;
import com.godel.employeemanagementrestful.repository.OrderTypeRepository;

public class InitializeOrderTypes {

	@Autowired
	OrderTypeRepository orderTypeRepository;

	public void saveOrderTypes() {
		
		List<OrderType> orderTypes = Arrays.asList(
		OrderType.builder()
	                .orderTypeName("Type A")
	                .price(new BigDecimal(2000))
	                .build(),
	    OrderType.builder()
	                .orderTypeName("Type B")
	                .price(new BigDecimal(1000))
	                .build(),
	    OrderType.builder()
	                .orderTypeName("Type C")
	                .price(new BigDecimal(1500))
	                .build(),
	    OrderType.builder()
	                .orderTypeName("Type D")
	                .price(new BigDecimal(400))
	                .build(),
	    OrderType.builder()
	                .orderTypeName("Type F")
	                .price(new BigDecimal(800))
	                .build(),
	    OrderType.builder()
	                .orderTypeName("Type G")
	                .price(new BigDecimal(2500))
	                .build()           
	    );
		for (OrderType orderType : orderTypes) {
			orderTypeRepository.save(orderType);
		}
		
	}

}
