package com.godel.employeemanagementrestful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godel.employeemanagementrestful.dto.UserDTO;
import com.godel.employeemanagementrestful.entity.OrderType;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.repository.OrderTypeRepository;

@RestController
@RequestMapping("/api/v1/ordertypes")
public class WorkOrderTypeController {
	
	@Autowired
	private OrderTypeRepository orderTypeRepository;
	
	@GetMapping("")
	public List<OrderType> getAllWorkOrderTypes() {
		return orderTypeRepository.findAll();
	}
	@PostMapping("")
	public OrderType saveWorkOrderType(@RequestBody OrderType orderType) {
		return orderTypeRepository.save(orderType);
	}
	
	@PutMapping("/{id}")
	public OrderType editOrderType(@RequestBody OrderType orderType) {
		return orderTypeRepository.save(orderType);
	}	

}
