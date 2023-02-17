package com.godel.employeemanagementrestful.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.godel.employeemanagementrestful.entity.Timetable;
import com.godel.employeemanagementrestful.entity.User;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {


	List<Timetable> findByUserAndDate(User user, YearMonth yearMonth);

	List<Timetable> findByUser(User user);
	
	@Query("SELECT t, u FROM Timetable t JOIN t.user u WHERE YEAR(t.date) = :year AND MONTH(t.date) = :month")
	List<Object[]> findTimetablesAndUsersForMonth(@Param("year") int year, @Param("month") int month);

	List<Timetable> findByUserAndDateBetween(User user, LocalDate atDay, LocalDate atEndOfMonth);

	List<Timetable> findByDateBetween(LocalDateTime of, LocalDateTime of2);

	List<Timetable> findByUserUserId(Long userId);

	List<Timetable> findByUserUserIdAndDateBetween(Long userId, LocalDateTime of, LocalDateTime of2);

	Timetable findByUserUserIdAndDate(Long userId, LocalDate currentDate);
}
