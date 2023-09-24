package com.godel.employeemanagementrestful.initialize;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.godel.employeemanagementrestful.entity.Customer;
import com.godel.employeemanagementrestful.entity.OrderType;
import com.godel.employeemanagementrestful.entity.Payroll;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.enums.OrderStatus;
import com.godel.employeemanagementrestful.repository.CustomerRepository;
import com.godel.employeemanagementrestful.repository.OrderTypeRepository;
import com.godel.employeemanagementrestful.repository.PayrollRepository;
import com.godel.employeemanagementrestful.repository.UserRepository;
import com.godel.employeemanagementrestful.repository.WorkOrderRepository;
import com.godel.employeemanagementrestful.service.PayrollService;
import com.godel.employeemanagementrestful.service.WorkOrderService;

@Component
public class InitializeWorkOrders {
	
	@Autowired
	WorkOrderRepository workOrderRepository;
	@Autowired
	OrderTypeRepository orderTypeRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	WorkOrderService workOrderService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PayrollRepository payrollRepository;
	
	private static final String[] ORDER_NAME_PREFIX = {"WAR", "GDA", "BRA", "LOD", "BEL",
			"GRO", "LGI", "OTW", "POL", "RAD", "KAT", "BIA", "SUW"};

    private static Random RANDOM = new Random();
    
    private static StringBuilder generateWorkOrderSuffix() {
    	StringBuilder sb = new StringBuilder();
    	int n1 = (int) (Math.random() * 9);
    	int n2 = (int) (Math.random() * 9);
    	int n3 = (int) (Math.random() * 9);
    	int n4 = (int) (Math.random() * 9);
    	return sb.append(n1).append(n2).append(n3).append(n4);
    	
    }
    
    private static String generateWorkOrderName() {
    	StringBuilder sb = new StringBuilder();
    	String prefix = ORDER_NAME_PREFIX[RANDOM.nextInt(ORDER_NAME_PREFIX.length)];
		sb.append(prefix).append(generateWorkOrderSuffix());
    	return sb.toString();
    	
    }

	public WorkOrder generateWorkOrder(OrderStatus status) {
		List<OrderType> orderTypes = orderTypeRepository.findAll();
		OrderType orderType = orderTypes.get(new Random().nextInt(orderTypes.size()));
        return WorkOrder.builder()
				.orderName(generateWorkOrderName())
				.orderType(orderType)
				.status(status)
				.comments("")
				.build();
	}

	@Transactional
	public void saveUnassignedWorkOrders(int numberOfWorkOrders) {
	    List<Customer> customers = customerRepository.findAll();
	    for (int i = 0; i < numberOfWorkOrders; i++) {
	        WorkOrder workOrder = generateWorkOrder(OrderStatus.UNASSIGNED);
	        workOrder = workOrderRepository.save(workOrder); 
	        Customer randomCustomer = customers.get(new Random().nextInt(customers.size()));
	        assignCustomerWithoutSaving(randomCustomer, workOrder);
	        workOrderRepository.save(workOrder); 
	    }
	}

	@Transactional
	public void saveWorkOrdersForUser(int numberOfWorkOrders, Long userId) {
	    List<Customer> customers = customerRepository.findAll();
	    for (int i = 0; i < numberOfWorkOrders; i++) {
	        WorkOrder workOrder = generateWorkOrder(OrderStatus.ACTIVE);
	        workOrder = workOrderRepository.save(workOrder); 
	        Customer randomCustomer = customers.get(new Random().nextInt(customers.size()));
	        assignCustomerWithoutSaving(randomCustomer, workOrder);
	        User user = userRepository.findById(userId).orElse(null); 
	        if (user != null) {
	            assignUserWithoutSaving(user, workOrder);
	        }
	        workOrder.setStartTimeStamp(LocalDateTime.now());
	        workOrder.setLastModificationTimeStamp(LocalDateTime.now());
	        workOrderRepository.save(workOrder); 
	    }
	}
	
    @Transactional
    public void saveWorkOrders(int numberOfWorkOrders) {
        List<Customer> customers = customerRepository.findAll();
        List<User> users = userRepository.findAll();
        for (int i=0; i<numberOfWorkOrders; i++) {
            WorkOrder workOrder = generateWorkOrder(OrderStatus.ACTIVE);
            workOrder = workOrderRepository.save(workOrder); 
            Customer randomCustomer = customers.get(new Random().nextInt(customers.size()));
            assignCustomerWithoutSaving(randomCustomer, workOrder);
            User randomUser = users.get(new Random().nextInt(users.size()));
            assignUserWithoutSaving(randomUser, workOrder);
            workOrderRepository.save(workOrder);  
        }
    }

    private void assignCustomerWithoutSaving(Customer customer, WorkOrder workOrder) {
        workOrder.setCustomer(customer);
        workOrder.setLastModificationTimeStamp(LocalDateTime.now());
    }

    private void assignUserWithoutSaving(User user, WorkOrder workOrder) {
        workOrder.setUser(user);  // Assuming you have a setUser method on WorkOrder
        workOrder.setLastModificationTimeStamp(LocalDateTime.now());
    }
	
	@Transactional
	public void randomizeTimeStampsForAllWorkOrders() {
	    List<WorkOrder> workOrders = workOrderRepository.findAll();
        long daysBetween = ChronoUnit.DAYS.between(LocalDateTime.of(2022,1,1, 0, 0), LocalDateTime.now());

		for (WorkOrder workOrder : workOrders) {
		    LocalDateTime startDate = LocalDateTime.of(2022,1,1, 0, 0)
		    		.plusDays((long)(Math.random() * (daysBetween-1)))
		    		.plusHours((long)(Math.random() * 24))
		    		.plusMinutes((long)(Math.random() * 60));
		    workOrder.setStartTimeStamp(startDate);    
		    OrderType orderType = workOrder.getOrderType();
		    if (orderType != null) {
		    	LocalDateTime endDate;
		        float days = orderType.getExpectedDays();
		        float variance = (float) (0.75 + Math.random() * 0.5);
		        float daysToAdd = days*variance;
		        int totalMinutesToAdd = (int) Math.ceil(daysToAdd * 24 * 60);
		        endDate = startDate.plusMinutes(totalMinutesToAdd);
		        if (endDate.isAfter(LocalDateTime.now())) {
	                endDate = LocalDateTime.now();
	            }	    
		        LocalDate payrollMonth = endDate.toLocalDate().withDayOfMonth(1); 
	    	    Payroll payroll = payrollRepository.findByUserAndPayrollMonth(workOrder.getUser(), payrollMonth);
	    	    if (payroll == null) {
	    	        System.out.println("No payroll found for user: " + workOrder.getUser().getUserId() + " for month: " + payrollMonth);
	    	        continue; 
	    	    }
	    	    
	    	    workOrder.setEndTimeStamp(endDate);
	    	    workOrder.setLastModificationTimeStamp(endDate);
	    	    workOrder.setStatus(OrderStatus.COMPLETED);
	    	    if (payroll != null) {
	    	        payroll.addAmount(workOrder.getOrderType().getPrice());
	    	    }
	    	    workOrder.setPayroll(payroll);
		        workOrderRepository.save(workOrder);
//		        payrollService.updatePayrollMoney(workOrder);
		    }
	    }
	}
	
	

}
