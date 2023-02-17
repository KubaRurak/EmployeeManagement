package com.godel.employeemanagementrestful.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

	WorkOrder findByOrderId(Long orderId);

	List<WorkOrder> findByUserUserId(Long userId);

	List<WorkOrder> findByLastModificationTimeStampBetween(LocalDateTime of, LocalDateTime of2);

	List<WorkOrder> findByUserUserIdAndLastModificationTimeStampBetween(Long userId, LocalDateTime of,
			LocalDateTime of2);

	List<WorkOrder> findByUserUserIdAndIsActiveTrue(Long userId);

	List<WorkOrder> findByIsActiveTrue();

	List<WorkOrder> findByCompletedTrueAndEndTimeStampBetween(LocalDateTime atStartOfDay, LocalDateTime atStartOfDay2);

	List<WorkOrder> findByUserAndEndTimeStampBefore(User user, LocalDateTime endOfMonth);

	List<WorkOrder> findByUserAndEndTimeStampBefore(User user, LocalDate atEndOfMonth);


}
