package com.godel.employeemanagementrestful.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
		name = "tbl_timetable"
)
public class Timetable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long timetableId;
  
  @NotNull
  @ManyToOne(
			cascade = CascadeType.ALL)
  @JoinColumn(
			name="user_id",
			referencedColumnName = "userId"
			)
  private User user;
  @NotNull
  private LocalDate date;
  
  @Column(name = "check_in_time",
		  nullable = true)
  private LocalTime checkIn;
  
  @Column(name = "check_out_time",
		  nullable = true)
  private LocalTime checkOut;
  private Duration trainingTime;
  private Duration breakTime;
  private Boolean onLeave;
  
  @Transient
  private Duration timeWorked;
  
  public Duration getTimeWorked() {
      if (checkIn != null && checkOut != null) {
          return Duration.between(checkIn, checkOut);
      }
      return Duration.ZERO;
  }
  
}
