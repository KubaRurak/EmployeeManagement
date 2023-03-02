package com.godel.employeemanagementrestful.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
		name = "tbl_order_type"
)
@Embeddable
public class OrderType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String orderTypeName;
	private Float expectedDays;
	private BigDecimal price;
	@JsonIgnore
	@OneToMany(
			mappedBy = "orderType",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
			)
	private List<WorkOrder> workOrders;
	

}
