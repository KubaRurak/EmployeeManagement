package com.godel.employeemanagementrestful.initialize;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("data-init")
public class DataInitializer implements CommandLineRunner {
	
	private static final int numberOfUsers=15; // will create at least 3
	private static final int numberOfCustomers=6;
	private static final int numberOfWorkOrders=2000;
	private static final LocalDate startDate = LocalDate.of(2022,1,1);
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
		initializeUsers.saveUsers(numberOfUsers);
		initializeCustomers.saveCustomers(numberOfCustomers);
		initializeOrderTypes.saveOrderTypes();
		initializeWorkOrders.saveWorkOrders(numberOfWorkOrders);
		initializeWorkOrders.randomizeTimeStampsForAllWorkOrders();
		initializeWorkOrders.saveUnassignedWorkOrders(25);
		initializeTimeTable.populateTimetable(startDate, endDate);
		initializeWorkOrders.saveWorkOrdersForUser(5, (long) 1);
		initializeWorkOrders.saveWorkOrdersForUser(5, (long) 2);
		
	}

}
