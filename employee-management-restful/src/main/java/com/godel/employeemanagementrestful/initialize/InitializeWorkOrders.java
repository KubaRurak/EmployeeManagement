package com.godel.employeemanagementrestful.initialize;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

import com.godel.employeemanagementrestful.entity.OrderType;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.initialize.generate.PersonGenerator;
import com.godel.employeemanagementrestful.repository.OrderTypeRepository;
import com.godel.employeemanagementrestful.repository.WorkOrderRepository;

public class InitializeWorkOrders {
	
	@Autowired
	WorkOrderRepository workOrderRepository;
	@Autowired
	OrderTypeRepository orderTypeRepository;
	
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



	public WorkOrder generateWorkOrder() {
        return WorkOrder.builder()
				.orderName(generateWorkOrderName())
				.orderType(orderTypeRepository.findById((long)Math.random()*7).get())
				.comments("")
				.build();
	}

	public void saveWorkOrders(int numberOfWorkOrders) {
		for (int i=0;i<numberOfWorkOrders;i++) {
			WorkOrder workOrder = generateWorkOrder();
			workOrderRepository.save(workOrder);
		}
		
	}		

}
