package com.godel.employeemanagementrestful.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.godel.employeemanagementrestful.dto.OrderTypeProfitDTO;
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

	List<WorkOrder> findByStatusAndLastModificationTimeStampBetween(OrderStatus status, LocalDateTime of,
			LocalDateTime of2);

	List<WorkOrder> findByUserUserIdAndStatusAndLastModificationTimeStampBetween(Long userId, OrderStatus status,
			LocalDateTime of, LocalDateTime of2);

	List<WorkOrder> findAllByOrderIdIn(List<Long> orderIds);

	Long countByStatus(OrderStatus status);

	List<WorkOrder> findTop4ByOrderByLastModificationTimeStampDesc();
	
    @Query("SELECT new com.godel.employeemanagementrestful.dto.OrderTypeProfitDTO(wo.orderType.orderTypeName, SUM(wo.orderType.price)) " +
            "FROM WorkOrder wo " +
            "GROUP BY wo.orderType.orderTypeName")
     List<OrderTypeProfitDTO> findProfitPerOrderType();


}
