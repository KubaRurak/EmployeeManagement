package com.godel.employeemanagementrestful.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.enums.OrderStatus;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

	WorkOrder findByOrderId(Long orderId);

	List<WorkOrder> findByUserUserId(Long userId);

	List<WorkOrder> findByLastModificationTimeStampBetween(LocalDateTime of, LocalDateTime of2);

	List<WorkOrder> findByUserUserIdAndLastModificationTimeStampBetween(Long userId, LocalDateTime of,
			LocalDateTime of2);

	List<WorkOrder> findByUserAndEndTimeStampBefore(User user, LocalDateTime endOfMonth);

	List<WorkOrder> findByUserAndEndTimeStampBefore(User user, LocalDate atEndOfMonth);

	List<WorkOrder> findByStatus(OrderStatus status);

	List<WorkOrder> findByUserUserIdAndStatus(Long userId, OrderStatus status);

	List<WorkOrder> findByEndTimeStampBetween(LocalDateTime of, LocalDateTime of2);

	List<WorkOrder> findByUserUserIdAndEndTimeStampBetween(Long userId, LocalDateTime of, LocalDateTime of2);


}
