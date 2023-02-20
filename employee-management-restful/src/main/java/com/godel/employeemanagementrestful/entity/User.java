package com.godel.employeemanagementrestful.entity;



import org.springframework.beans.factory.annotation.Value;

import com.godel.employeemanagementrestful.enums.OfficeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private String emailId;
	@Value("1234")
	private String password;
	private String firstName;
	private String lastName;
	private OfficeCode officeCode;
	private String role;
	private Boolean isEmployed;
//	@JsonIgnore
//	@OneToMany(
//			mappedBy = "user",
//			cascade = CascadeType.ALL,
//			fetch = FetchType.LAZY
//			)
//	private List<WorkOrder> workOrders;

}
