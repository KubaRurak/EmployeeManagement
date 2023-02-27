package com.godel.employeemanagementrestful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.godel.employeemanagementrestful.dto.UserDTO;
import com.godel.employeemanagementrestful.entity.OrderType;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.repository.OrderTypeRepository;

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
	@PostMapping("")
	public List<OrderType> saveWorkOrderType(@RequestBody List<OrderType> orderTypes) {
		return orderTypeRepository.save(orderTypes);
	}	
	
	@PutMapping("/{id}")
	public OrderType editOrderType(@RequestBody OrderType orderType) {
		return orderTypeRepository.save(orderType);
	}	

}
