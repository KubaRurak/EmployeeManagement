package com.godel.employeemanagementrestful.initialize;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("data-init")
public class DataInitializer implements CommandLineRunner {
	
	private static final int numberOfUsers=10;
	private static final int numberOfCustomers=4;
	private static final int numberOfWorkOrders=1000;
	private static final LocalDate startDate = LocalDate.of(2022,2,1);
	private static final LocalDate endDate = LocalDate.now().minusDays(1);
	
	@Autowired
	InitializeUsers initializeUsers;
	@Autowired
	InitializeCustomers initializeCustomers;
	@Autowired
	InitializeOrderTypes initializeOrderTypes;
	@Autowired
	InitializeWorkOrders initializeWorkOrders;
	@Autowired
	InitializeTimeTable initializeTimeTable;
	
	@Override
	public void run(String... args) throws Exception {
//		initializeUsers.saveUsers(numberOfUsers);
//		initializeCustomers.saveCustomers(numberOfCustomers);
//		initializeOrderTypes.saveOrderTypes();
//		initializeWorkOrders.saveWorkOrders(numberOfWorkOrders);
//		initializeWorkOrders.randomizeTimeStampsForAllWorkOrders();
//		initializeTimeTable.populateTimetable(startDate, endDate);
//		initializeWorkOrders.saveWorkOrdersForUser(5, new Long(1));
//		initializeWorkOrders.saveWorkOrdersForUser(5, new Long(2));
		
	}

}
