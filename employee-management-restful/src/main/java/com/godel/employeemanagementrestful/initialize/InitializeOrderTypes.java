package com.godel.employeemanagementrestful.initialize;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.godel.employeemanagementrestful.entity.OrderType;
import com.godel.employeemanagementrestful.repository.OrderTypeRepository;
@Component
public class InitializeOrderTypes {

	@Autowired
	OrderTypeRepository orderTypeRepository;

	public void saveOrderTypes() {
		
		List<OrderType> orderTypes = Arrays.asList(
		OrderType.builder()
	                .orderTypeName("Type A")
	                .price(new BigDecimal(10000))
	                .expectedDays(10.0f)
	                .build(),
	    OrderType.builder()
	                .orderTypeName("Type B")
	                .price(new BigDecimal(3000))
	                .expectedDays(4.0f)
	                .build(),
	    OrderType.builder()
	                .orderTypeName("Type C")
	                .price(new BigDecimal(5000))
	                .expectedDays(5.0f)
	                .build(),
	    OrderType.builder()
	                .orderTypeName("Type D")
	                .price(new BigDecimal(900))
	                .expectedDays(1.0f)
	                .build(),
	    OrderType.builder()
	                .orderTypeName("Type E")
	                .price(new BigDecimal(1300))
	                .expectedDays(1.5f)
	                .build(),
	    OrderType.builder()
	                .orderTypeName("Type F")
	                .price(new BigDecimal(1500))
	                .expectedDays(1.3f)
	                .build(),
	    OrderType.builder()
	                .orderTypeName("Type G")
	                .price(new BigDecimal(4500))
	                .expectedDays(5.0f)
	                .build()           
	    );
		for (OrderType orderType : orderTypes) {
			orderTypeRepository.save(orderType);
		}
		
	}

}
