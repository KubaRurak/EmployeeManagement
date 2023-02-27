package com.godel.employeemanagementrestful.entity;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
		name = "tbl_customer",
		uniqueConstraints = @UniqueConstraint(
				name="emailid_unique",
				columnNames="email_adress"				
		)	
)
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	@Column(name="email_adress")
	@Email
	@NotNull
	private String emailId;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private String Company;
	@JsonIgnore
	@OneToMany(
			mappedBy = "customer",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
			)
	private List<WorkOrder> workOrders;

}
