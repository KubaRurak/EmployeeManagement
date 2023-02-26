package com.godel.employeemanagementrestful.entity;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;

import com.godel.employeemanagementrestful.enums.OrderStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@NonNull
	private String orderName;
	@NonNull
	@Embedded
	@ManyToOne
	private OrderType orderType;
	@Column(name = "completed", nullable = false)
	@Value("false")
	private Boolean completed;
	@Column(name = "canceled", nullable = false)
	@Value("false")
	private Boolean canceled;
	@Column(name = "isActive", nullable = false)
	@Value("false")
	private	Boolean isActive;
	@Enumerated(EnumType.STRING)
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
	@ManyToOne(
			cascade = CascadeType.ALL)
	@JoinColumn(
			name="customer_id",
			referencedColumnName = "customerId"
			)	
	private Customer customer;

}
