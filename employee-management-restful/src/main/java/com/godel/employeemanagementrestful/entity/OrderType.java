package com.godel.employeemanagementrestful.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
	
	@Column(name="order_type_name")
	@NotBlank(message = "Order type name cannot be blank")
	private String orderTypeName;
	
	@Column(name="expected_days")
    @NotNull(message = "Expected days cannot be null")
    @PositiveOrZero(message = "Expected days must be a non negative number")
	private Float expectedDays;
    
	@Column(name="price")
    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Expected days must be a non negative number")
	private BigDecimal price;
    
	@JsonIgnore
	@OneToMany(
			mappedBy = "orderType",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
			)
	private List<WorkOrder> workOrders;
	

}
