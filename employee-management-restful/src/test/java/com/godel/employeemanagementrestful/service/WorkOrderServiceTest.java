package com.godel.employeemanagementrestful.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.godel.employeemanagementrestful.config.TestConfig;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.enums.OrderStatus;
import com.godel.employeemanagementrestful.repository.CustomerRepository;
import com.godel.employeemanagementrestful.repository.PayrollRepository;
import com.godel.employeemanagementrestful.repository.UserRepository;
import com.godel.employeemanagementrestful.repository.WorkOrderRepository;

@SpringBootTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class WorkOrderServiceTest {

    @InjectMocks
    private WorkOrderService workOrderService;

    @MockBean
    private WorkOrderRepository workOrderRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PayrollRepository payrollRepository;

    @MockBean
    private CustomerRepository customerRepository;
    

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("When there are no WorkOrders in the given date range, the returned list should be empty.")
    void testGetFilteredWorkOrders_NoWorkOrdersInRange() {
        // Arrange
        LocalDate after = LocalDate.now().minusDays(5);
        LocalDate before = LocalDate.now();
        when(workOrderRepository.findByLastModificationTimeStampBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Collections.emptyList());

        // Act
        List<WorkOrder> result = workOrderService.getFilteredWorkOrders(null, after, before);

        // Assert
        assertTrue(result.isEmpty());
    }
    
    @Test
    @DisplayName("When there are multiple WorkOrders in the given date range, all should be returned.")
    void testGetFilteredWorkOrders_MultipleWorkOrdersInRange() {
        // Arrange
        LocalDate after = LocalDate.now().minusDays(5);
        LocalDate before = LocalDate.now();
        WorkOrder workOrder1 = new WorkOrder();
        WorkOrder workOrder2 = new WorkOrder();
        when(workOrderRepository.findByLastModificationTimeStampBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Arrays.asList(workOrder1, workOrder2));

        // Act
        List<WorkOrder> result = workOrderService.getFilteredWorkOrders(null, after, before);

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(workOrder1, workOrder2)));
    }
    
    @Test
    @DisplayName("When all WorkOrders are outside the given date range, the returned list should be empty.")
    void testGetFilteredWorkOrders_WorkOrdersOutsideRange() {
        // Arrange
        LocalDate after = LocalDate.now().minusDays(5);
        LocalDate before = LocalDate.now();
        when(workOrderRepository.findByLastModificationTimeStampBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Collections.emptyList());

        // Act
        List<WorkOrder> result = workOrderService.getFilteredWorkOrders(null, after, before);

        // Assert
        assertTrue(result.isEmpty());
    }
    
    @Test
    void testGetAllWorkOrders() {
        // Arrange
        WorkOrder workOrder = new WorkOrder();
        when(workOrderRepository.findAll()).thenReturn(Collections.singletonList(workOrder));
        
        // Act
        List<WorkOrder> result = workOrderService.getFilteredWorkOrders(null, null, null);
        
        // Assert
        assertEquals(1, result.size());
        assertEquals(workOrder, result.get(0));
        verify(workOrderRepository).findAll();
    }
    
    @Test
    void testGetWorkOrdersByUserId() {
        // Arrange
        Long userId = 1L;
        WorkOrder workOrder = new WorkOrder();
        when(workOrderRepository.findByUserUserId(userId)).thenReturn(Collections.singletonList(workOrder));
        
        // Act
        List<WorkOrder> result = workOrderService.getFilteredWorkOrders(userId, null, null);
        
        // Assert
        assertEquals(1, result.size());
        assertEquals(workOrder, result.get(0));
        verify(workOrderRepository).findByUserUserId(userId);
    }
    
    @Test
    void testGetWorkOrdersByUserIdAndDateRange() {
        // Arrange
        Long userId = 1L;
        LocalDate after = LocalDate.now().minusDays(5);
        LocalDate before = LocalDate.now();
        WorkOrder workOrder = new WorkOrder();
        when(workOrderRepository.findByUserUserIdAndLastModificationTimeStampBetween(eq(userId), any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Collections.singletonList(workOrder));
        
        // Act
        List<WorkOrder> result = workOrderService.getFilteredWorkOrders(userId, after, before);
        
        // Assert
        assertEquals(1, result.size());
        assertEquals(workOrder, result.get(0));
        verify(workOrderRepository).findByUserUserIdAndLastModificationTimeStampBetween(userId, LocalDateTime.of(after, LocalTime.MIN), LocalDateTime.of(before, LocalTime.MAX));
    }
    
    @Test
    void testGetWorkOrdersByStatusOnly() {
        // Arrange
        OrderStatus status = OrderStatus.COMPLETED;
        WorkOrder workOrder = new WorkOrder();
        when(workOrderRepository.findByStatus(status)).thenReturn(Collections.singletonList(workOrder));

        // Act
        List<WorkOrder> result = workOrderService.getWorkOrdersByStatus(null, null, null, status);

        // Assert
        assertEquals(1, result.size());
        assertEquals(workOrder, result.get(0));
        verify(workOrderRepository).findByStatus(status);
    }
    
    @Test
    void testGetWorkOrdersByStatusAndDateRange() {
        // Arrange
        OrderStatus status = OrderStatus.COMPLETED;
        LocalDate after = LocalDate.now().minusDays(5);
        LocalDate before = LocalDate.now();
        WorkOrder workOrder = new WorkOrder();
        when(workOrderRepository.findByStatusAndLastModificationTimeStampBetween(eq(status), any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Collections.singletonList(workOrder));

        // Act
        List<WorkOrder> result = workOrderService.getWorkOrdersByStatus(null, after, before, status);

        // Assert
        assertEquals(1, result.size());
        assertEquals(workOrder, result.get(0));
        verify(workOrderRepository).findByStatusAndLastModificationTimeStampBetween(status, LocalDateTime.of(after, LocalTime.MIN), LocalDateTime.of(before, LocalTime.MAX));
    }
    
    @Test
    void testGetWorkOrdersByUserIdAndStatus() {
        // Arrange
        Long userId = 1L;
        OrderStatus status = OrderStatus.COMPLETED;
        WorkOrder workOrder = new WorkOrder();
        when(workOrderRepository.findByUserUserIdAndStatus(userId, status)).thenReturn(Collections.singletonList(workOrder));

        // Act
        List<WorkOrder> result = workOrderService.getWorkOrdersByStatus(userId, null, null, status);

        // Assert
        assertEquals(1, result.size());
        assertEquals(workOrder, result.get(0));
        verify(workOrderRepository).findByUserUserIdAndStatus(userId, status);
    }
    
    @Test
    void testGetWorkOrdersByUserIdStatusAndDateRange() {
        // Arrange
        Long userId = 1L;
        OrderStatus status = OrderStatus.COMPLETED;
        LocalDate after = LocalDate.now().minusDays(5);
        LocalDate before = LocalDate.now();
        WorkOrder workOrder = new WorkOrder();
        when(workOrderRepository.findByUserUserIdAndStatusAndLastModificationTimeStampBetween(eq(userId), eq(status), any(LocalDateTime.class), any(LocalDateTime.class)))
            .thenReturn(Collections.singletonList(workOrder));

        // Act
        List<WorkOrder> result = workOrderService.getWorkOrdersByStatus(userId, after, before, status);

        // Assert
        assertEquals(1, result.size());
        assertEquals(workOrder, result.get(0));
        verify(workOrderRepository).findByUserUserIdAndStatusAndLastModificationTimeStampBetween(userId, status, LocalDateTime.of(after, LocalTime.MIN), LocalDateTime.of(before, LocalTime.MAX));
    }
}
