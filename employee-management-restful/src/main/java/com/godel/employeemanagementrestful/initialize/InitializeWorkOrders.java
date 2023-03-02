package com.godel.employeemanagementrestful.initialize;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.godel.employeemanagementrestful.entity.Customer;
import com.godel.employeemanagementrestful.entity.OrderType;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.enums.OrderStatus;
import com.godel.employeemanagementrestful.repository.CustomerRepository;
import com.godel.employeemanagementrestful.repository.OrderTypeRepository;
import com.godel.employeemanagementrestful.repository.UserRepository;
import com.godel.employeemanagementrestful.repository.WorkOrderRepository;
import com.godel.employeemanagementrestful.service.PayrollService;
import com.godel.employeemanagementrestful.service.WorkOrderService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
	PayrollService payrollService;
//    @PersistenceContext
//    private EntityManager entityManager;
	
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
//	public WorkOrder generateWorkOrder(Customer customer, User user) {
//		List<OrderType> orderTypes = orderTypeRepository.findAll();
//		OrderType orderType = orderTypes.get(new Random().nextInt(orderTypes.size()));
//        WorkOrder workOrder = WorkOrder.builder()
//				.orderName(generateWorkOrderName())
//				.orderType(orderType)
//				.status(OrderStatus.UNASSIGNED)
//				.user(user)
//				.customer(customer)
//				.comments("")
//				.build();
//        
//        Customer mergedCustomer = entityManager.merge(customer);
//        workOrder.setCustomer(mergedCustomer);
//        User mergedUser = entityManager.merge(user);
//        workOrder.setUser(mergedUser);        
//        return workOrder;
//        
//	}
//
//	public void saveWorkOrders(int numberOfWorkOrders) {
//	    List<Customer> customers = customerRepository.findAll();
//	    List<User> users = userRepository.findAll();
//		for (int i=0;i<numberOfWorkOrders;i++) {
//			Customer customer = customers.get(new Random().nextInt(customers.size()));
//			User user = users.get(new Random().nextInt(users.size()));
//			WorkOrder workOrder = generateWorkOrder(customer, user);
//			workOrderRepository.save(workOrder);
//
//		}
//	}
	public WorkOrder generateWorkOrder() {
		List<OrderType> orderTypes = orderTypeRepository.findAll();
		OrderType orderType = orderTypes.get(new Random().nextInt(orderTypes.size()));
        return WorkOrder.builder()
				.orderName(generateWorkOrderName())
				.orderType(orderType)
				.status(OrderStatus.UNASSIGNED)
				.comments("")
				.build();
	}

	public void saveWorkOrders(int numberOfWorkOrders) {
	    List<Customer> customers = customerRepository.findAll();
	    List<User> users = userRepository.findAll();
		for (int i=0;i<numberOfWorkOrders;i++) {
			WorkOrder workOrder = generateWorkOrder();
			workOrderRepository.save(workOrder);
			Long workOrderId = workOrder.getOrderId();
	        workOrderService.assignCustomerToWorkOrder(
	        		customers.get(new Random().nextInt(customers.size()))
	        		.getCustomerId(), workOrderId);
	        workOrderService.assignUserToWorkOrder(users.get(new Random().nextInt(users.size()))
	        		.getUserId(), workOrderId);
		}
	}
	
	public void randomizeTimeStampsForAllWorkOrders() {
	    List<WorkOrder> workOrders = workOrderRepository.findAll();
		for (WorkOrder workOrder : workOrders) {
		    LocalDateTime startDate = LocalDateTime.now()
		    		.minusYears(1)
		    		.plusDays((long)(Math.random() * 364))
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
		        workOrder.setEndTimeStamp(endDate);
		        workOrder.setStatus(OrderStatus.COMPLETED);
		        workOrderRepository.save(workOrder);
		        payrollService.updatePayrollMoney(workOrder);
		    }
	    }
	}
	
	

}
