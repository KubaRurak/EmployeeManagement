package com.godel.employeemanagementrestful.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.godel.employeemanagementrestful.config.TestConfig;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.exceptions.ResourceNotFoundException;
import com.godel.employeemanagementrestful.repository.UserRepository;

@SpringBootTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class UserServiceTest {
    
    @InjectMocks
    UserService userService;
    
    @Mock
    UserRepository userRepository;
    
    @Mock
    TimetableService timetableService;

    @Mock
    PayrollService payrollService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should save user, populate timetable, and generate payroll")
    void testSaveUser() {
        // Arrange
        User user = new User();
        user.setUserId(1L);
        
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(eq(user.getUserId()))).thenReturn(Optional.of(user));
        
        // Act
        User savedUser = userService.saveUser(user);
        
        // Assert
        assertNotNull(savedUser);
        assertEquals(user.getUserId(), savedUser.getUserId());
        verify(userRepository).save(user);
        verify(timetableService).populateTimetable(eq(savedUser), eq(LocalDate.of(2022, 1, 1)), eq(LocalDate.now().plusYears(1)));
        verify(payrollService).generatePayrollForUser(YearMonth.of(2022, 1), 24, savedUser.getUserId());
    }

    @Test
    @DisplayName("Should throw exception when saved user is not found")
    void testSaveUserNotFoundException() {
        // Arrange
        User user = new User();
        user.setUserId(1L);

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(eq(user.getUserId()))).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.saveUser(user));
    }
}
