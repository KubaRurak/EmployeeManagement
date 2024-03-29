package com.godel.employeemanagementrestful.entity;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@NotNull(message = "Email address cannot be null")
	@Email(message = "Email address is not valid")
	private String emailId;
	
	@Column(name="first_name")
	@NotNull(message = "First name cannot be null")
	@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters long")
	private String firstName;
	
	@Column(name="last_name")
	@NotNull(message = "First name cannot be null")
	@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters long")
	private String lastName;
	
	@Column(name="company_name")
	@NotNull(message = "First name cannot be null")
	@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters long")
	private String companyName;
	
	@JsonIgnore
	@OneToMany(
			mappedBy = "customer",
			cascade = {CascadeType.PERSIST, CascadeType.MERGE},
			fetch = FetchType.LAZY
			)
	private List<WorkOrder> workOrders;

}
