package com.godel.employeemanagementrestful.initialize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("data-init")
public class DataInitializer implements CommandLineRunner {
	
	private static final int numberOfUsers=30;
	private static final int numberOfCustomers=5;
	private static final int numberOfWorkOrders=100;
	
	@Autowired
	InitializeUsers initializeUsers;
	@Autowired
	InitializeCustomers initializeCustomers;
	@Autowired
	InitializeOrderTypes initializeOrderTypes;
	@Autowired
	InitializeWorkOrders initializeWorkOrders;
	
	@Override
	public void run(String... args) throws Exception {
//		initializeUsers.saveUsers(numberOfUsers);
//		initializeCustomers.saveCustomers(numberOfCustomers);
//		initializeOrderTypes.saveOrderTypes();
		initializeWorkOrders.saveWorkOrders(numberOfWorkOrders);
//		initializeWorkOrders.randomizeTimeStampsForAllWorkOrders();
		
		
	}

}
