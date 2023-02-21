package com.godel.employeemanagementrestful.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.godel.employeemanagementrestful.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
		name = "tbl_work_order"
)
public class WorkOrder {
	@Id
	@SequenceGenerator(
			name = "workorder_sequence",
			sequenceName = "workorder_sequence",
			initialValue = 100000,
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "workorder_sequence"
			)
	private Long orderId;
	private String orderName;
	private String orderType;
	private BigDecimal price;
	@Column(name = "completed", nullable = false)
	@Value("false")
	private Boolean completed;
	@Column(name = "canceled", nullable = false)
	@Value("false")
	private Boolean canceled;
	@Column(name = "isActive", nullable = false)
	@Value("false")
	private	Boolean isActive;
	private OrderStatus status;
	private LocalDateTime startTimeStamp;
	private LocalDateTime endTimeStamp;
	private LocalDateTime lastModificationTimeStamp;
	private String comments;
	@ManyToOne(
			cascade = CascadeType.ALL)
	@JoinColumn(
			name="user_id",
			referencedColumnName = "userId"
			)
	private User user;

}
