package com.godel.employeemanagementrestful.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godel.employeemanagementrestful.entity.OrderType;

public interface OrderTypeRepository extends JpaRepository<OrderType, Long> {

	List<OrderType> save(List<OrderType> orderTypes);

}
