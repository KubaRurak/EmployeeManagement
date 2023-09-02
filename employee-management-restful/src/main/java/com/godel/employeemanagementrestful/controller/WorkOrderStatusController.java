package com.godel.employeemanagementrestful.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.godel.employeemanagementrestful.enums.OrderStatus;

@RestController
@RequestMapping("/api/v1/statustypes")
public class WorkOrderStatusController {
	
    @GetMapping("")
    public ResponseEntity<List<OrderStatus>> getOrderStatuses() {
        return new ResponseEntity<List<OrderStatus>>(Arrays.asList(OrderStatus.values()), HttpStatus.OK);

    }

}
