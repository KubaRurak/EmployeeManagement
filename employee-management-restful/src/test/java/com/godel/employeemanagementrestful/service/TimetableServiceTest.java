package com.godel.employeemanagementrestful.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.godel.employeemanagementrestful.config.TestConfig;
import com.godel.employeemanagementrestful.entity.Timetable;
import com.godel.employeemanagementrestful.exceptions.TimetableException;
import com.godel.employeemanagementrestful.repository.TimetableRepository;

@SpringBootTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class TimetableServiceTest {
	
    @InjectMocks
	TimetableService timetableService;
    
    @MockBean
	TimetableRepository timetableRepository;
    
    @MockBean
    private PayrollService payrollService;    

	
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Nested
    @DisplayName("Tests for getFilteredTimetable")
    class GetFilteredTimetableTests {
		@Test
	    @DisplayName("Should get all timetables")
		void testGetAllTimetables() {
		    // Arrange
		    Timetable timetable = new Timetable();
		    when(timetableRepository.findAll()).thenReturn(Collections.singletonList(timetable));
	
		    // Act
		    List<Timetable> result = timetableService.getFilteredTimetable(null, null, null);
	
		    // Assert
		    assertEquals(1, result.size());
		    assertEquals(timetable, result.get(0));
		    verify(timetableRepository).findAll();
		}
		@Test
	    @DisplayName("Should get timetables by date range")
		void testGetTimetablesByDateRange() {
		    // Arrange
		    LocalDate after = LocalDate.now().minusDays(5);
		    LocalDate before = LocalDate.now();
		    Timetable timetable = new Timetable();
		    when(timetableRepository.findByDateBetween(after, before)).thenReturn(Collections.singletonList(timetable));
	
		    // Act
		    List<Timetable> result = timetableService.getFilteredTimetable(null, after, before);
	
		    // Assert
		    assertEquals(1, result.size());
		    assertEquals(timetable, result.get(0));
		    verify(timetableRepository).findByDateBetween(after, before);
		}
		
		@Test
	    @DisplayName("Should get timetables by user ID")
		void testGetTimetablesByUserId() {
		    // Arrange
		    Long userId = 1L;
		    Timetable timetable = new Timetable();
		    when(timetableRepository.findByUserUserId(userId)).thenReturn(Collections.singletonList(timetable));
	
		    // Act
		    List<Timetable> result = timetableService.getFilteredTimetable(userId, null, null);
	
		    // Assert
		    assertEquals(1, result.size());
		    assertEquals(timetable, result.get(0));
		    verify(timetableRepository).findByUserUserId(userId);
		}
		
		@Test
	    @DisplayName("Should get timetables by user ID and date range")
		void testGetTimetablesByUserIdAndDateRange() {
		    // Arrange
		    Long userId = 1L;
		    LocalDate after = LocalDate.now().minusDays(5);
		    LocalDate before = LocalDate.now();
		    Timetable timetable = new Timetable();
		    when(timetableRepository.findByUserUserIdAndDateBetween(userId, after, before)).thenReturn(Collections.singletonList(timetable));
	
		    // Act
		    List<Timetable> result = timetableService.getFilteredTimetable(userId, after, before);
	
		    // Assert
		    assertEquals(1, result.size());
		    assertEquals(timetable, result.get(0));
		    verify(timetableRepository).findByUserUserIdAndDateBetween(userId, after, before);
		}
    }
	
    @Nested
    @DisplayName("Tests for checkIn")
	class CheckInTests {
    	
    	@Test
        @DisplayName("Should check in successfully")
		void testCheckInSuccessfully() throws TimetableException {
		    // Arrange
		    Long userId = 1L;
		    Timetable timetable = new Timetable();
		    when(timetableRepository.findByUserUserIdAndDate(eq(userId), any(LocalDate.class))).thenReturn(timetable);
	
		    // Act
		    timetableService.checkIn(userId);
	
		    // Assert
		    assertNotNull(timetable.getCheckIn());
		    verify(timetableRepository).save(timetable);
		}
		
        @Test
        @DisplayName("Should not allow check in if already checked in")
		void testCheckInAlreadyCheckedIn() {
		    // Arrange
		    Long userId = 1L;
		    Timetable timetable = new Timetable();
		    timetable.setCheckIn(LocalTime.now());
		    when(timetableRepository.findByUserUserIdAndDate(eq(userId), any(LocalDate.class))).thenReturn(timetable);
	
		    // Act & Assert
		    assertThrows(TimetableException.class, () -> timetableService.checkIn(userId));
		}
    }
	
    @Nested
    @DisplayName("Tests for checkOut")
    class CheckOutTests {
        @Test
        @DisplayName("Should check out successfully")
		void testCheckOutSuccessfully() throws TimetableException {
		    // Arrange
		    Long userId = 1L;
		    Timetable timetable = new Timetable();
		    timetable.setCheckIn(LocalTime.now());
		    when(timetableRepository.findByUserUserIdAndDate(eq(userId), any(LocalDate.class))).thenReturn(timetable);
	
		    // Act
		    timetableService.checkOut(userId);
	
		    // Assert
		    assertNotNull(timetable.getCheckOut());
		    verify(timetableRepository).save(timetable);
		    verify(payrollService).updatePayrollTime(eq(userId), eq(timetable));
		}
		
        @Test
        @DisplayName("Should not allow check out without checking in")
		void testCheckOutWithoutCheckingIn() {
		    // Arrange
		    Long userId = 1L;
		    Timetable timetable = new Timetable();
		    when(timetableRepository.findByUserUserIdAndDate(eq(userId), any(LocalDate.class))).thenReturn(timetable);
	
		    // Act & Assert
		    assertThrows(TimetableException.class, () -> timetableService.checkOut(userId));
		}
        @Test
        @DisplayName("Should not allow check out if already checked out")
		void testCheckOutAlreadyCheckedOut() {
		    // Arrange
		    Long userId = 1L;
		    Timetable timetable = new Timetable();
		    timetable.setCheckIn(LocalTime.now());
		    timetable.setCheckOut(LocalTime.now());
		    when(timetableRepository.findByUserUserIdAndDate(eq(userId), any(LocalDate.class))).thenReturn(timetable);
	
		    // Act & Assert
		    assertThrows(TimetableException.class, () -> timetableService.checkOut(userId));
		}
    }
    
    @Nested
    @DisplayName("Tests for getting check-in and check-out times")
    class CheckTimeTests {

        @Test
        @DisplayName("Should retrieve today's check-in time")
        void testGetTodayCheckInTime() {
            // Arrange
            Long userId = 1L;
            LocalTime expectedCheckInTime = LocalTime.of(9, 0);
            Timetable timetable = new Timetable();
            timetable.setCheckIn(expectedCheckInTime);

            when(timetableRepository.findByUserUserIdAndDate(eq(userId), any(LocalDate.class)))
                .thenReturn(timetable);

            // Act
            LocalTime actualCheckInTime = timetableService.getTodayCheckInTime(userId);

            // Assert
            assertEquals(expectedCheckInTime, actualCheckInTime);
            verify(timetableRepository).findByUserUserIdAndDate(eq(userId), any(LocalDate.class));
        }

        @Test
        @DisplayName("Should retrieve today's check-out time")
        void testGetTodayCheckOutTime() {
            // Arrange
            Long userId = 1L;
            LocalTime expectedCheckOutTime = LocalTime.of(17, 0);
            Timetable timetable = new Timetable();
            timetable.setCheckOut(expectedCheckOutTime);

            when(timetableRepository.findByUserUserIdAndDate(eq(userId), any(LocalDate.class)))
                .thenReturn(timetable);

            // Act
            LocalTime actualCheckOutTime = timetableService.getTodayCheckOutTime(userId);

            // Assert
            assertEquals(expectedCheckOutTime, actualCheckOutTime);
            verify(timetableRepository).findByUserUserIdAndDate(eq(userId), any(LocalDate.class));
        }
    }
    
    @Nested
    @DisplayName("Tests for editing check-in and check-out times")
    class EditTimesTests {

        @Test
        @DisplayName("Should edit check-in and check-out times for a specific date")
        void testEditCheckTimesForDate() {
            // Arrange
            Long userId = 1L;
            LocalDate date = LocalDate.of(2023, 10, 1);
            LocalTime checkIn = LocalTime.of(9, 0);
            LocalTime checkOut = LocalTime.of(17, 0);
            Timetable timetable = new Timetable();

            when(timetableRepository.findByUserUserIdAndDate(eq(userId), eq(date)))
                .thenReturn(timetable);

            // Act
            timetableService.editCheckTimesForDate(userId, date, checkIn, checkOut);

            // Assert
            assertEquals(checkIn, timetable.getCheckIn());
            assertEquals(checkOut, timetable.getCheckOut());
            verify(timetableRepository).save(timetable);
        }
    }
	
	
	
}
