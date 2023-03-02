package com.godel.employeemanagementrestful.entity;



import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.godel.employeemanagementrestful.enums.OfficeCode;
import com.godel.employeemanagementrestful.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
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
		name = "tbl_user",
		uniqueConstraints = @UniqueConstraint(
				name="emailid_unique",
				columnNames="email_adress"				
		)	
)
public class User {
	
	@Id
	@SequenceGenerator(
			name = "user_sequence",
			sequenceName = "user_sequence",
			allocationSize = 1
	)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "user_sequence"
			)
	private Long userId;
	@Column(name="email_adress")
	@NotNull(message = "Email address cannot be null")
	@Email(message = "Email address is not valid")
	private String emailId;
	
	@Column(name="password")
	@NotNull(message = "Password cannot be null")
	@Size(min = 6, message = "Password must be at least 6 characters long")
	private String password;
	
	@Column(name="first_name")
	@NotNull(message = "First name cannot be null")
	@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters long")
	private String firstName;
	
	@Column(name="last_name")
	@NotNull(message = "First name cannot be null")
	@Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters long")
	private String lastName;
	
	@Column(name="office_code")
	@NotNull(message = "Office code cannot be null")
	@Enumerated(EnumType.STRING)
	private OfficeCode officeCode;
	
	@Column(name="user_role")
	@NotNull(message = "User role cannot be null")
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Column(name="is_employed")
	@NotNull(message = "Employment status cannot be null")
	private Boolean isEmployed;
	@JsonIgnore
	@OneToMany(
			mappedBy = "user",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY
			)
	private List<WorkOrder> workOrders;

}
