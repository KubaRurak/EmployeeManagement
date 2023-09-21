package com.godel.employeemanagementrestful.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<List<OrderType>> getAllWorkOrderTypes() {
	    List<OrderType> orderTypes = orderTypeRepository.findAll();
	    return ResponseEntity.ok(orderTypes);
	}

	@PostMapping("")
	public ResponseEntity<OrderType> saveWorkOrderType(@RequestBody OrderType orderType) {
	    OrderType savedOrderType = orderTypeRepository.save(orderType);
	    return new ResponseEntity<>(savedOrderType, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<OrderType> editOrderType(@RequestBody OrderType orderType) {
	    if (!orderTypeRepository.existsById(orderType.getId())) {
	        return ResponseEntity.notFound().build();
	    }
	    OrderType updatedOrderType = orderTypeRepository.save(orderType);
	    return ResponseEntity.ok(updatedOrderType);
	}

}
