package com.godel.employeemanagementrestful.entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Timetable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer timetableId;
  
  @ManyToOne(
			cascade = CascadeType.ALL)
  @JoinColumn(
			name="user_id",
			referencedColumnName = "userId"
			)
  private User user;
  
  private LocalDate date;
  
  @Column(nullable = true)
  private LocalTime checkIn;
  
  @Column(nullable = true)
  private LocalTime checkOut;
  
  @Transient
  private Duration workedHours;
  
  public Duration getWorkedHours() {
      if (checkIn != null && checkOut != null) {
          return Duration.between(checkIn, checkOut);
      }
      return Duration.ZERO;
  }
  
}
