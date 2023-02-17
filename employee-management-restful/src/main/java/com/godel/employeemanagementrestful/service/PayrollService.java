package com.godel.employeemanagementrestful.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godel.employeemanagementrestful.dto.PayrollDTO;
import com.godel.employeemanagementrestful.entity.Payroll;
import com.godel.employeemanagementrestful.entity.Timetable;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.repository.PayrollRepository;
import com.godel.employeemanagementrestful.repository.TimetableRepository;
import com.godel.employeemanagementrestful.repository.UserRepository;
import com.godel.employeemanagementrestful.repository.WorkOrderRepository;

import jakarta.transaction.Transactional;

@Service
public class PayrollService {
    
    @Autowired
    private WorkOrderRepository workOrderRepository;

    @Autowired
    private TimetableRepository timetableRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PayrollRepository payrollRepository;
    
    @Transactional
    public void generatePayroll(YearMonth yearMonth) {
        List<Payroll> payrolls = new ArrayList<>();

        // Retrieve the users and their timetables for the specified month
        List<User> users = userRepository.findAll();

        // Loop through the results, calculating the hours worked and money generated for each user
        for (User user : users) {
            BigDecimal hoursWorked = BigDecimal.ZERO;
            
            List<Timetable> timetables = timetableRepository.findByUserAndDateBetween(user, yearMonth.atDay(1), yearMonth.atEndOfMonth());
            for (Timetable timetable : timetables) {
	            if (timetable.getCheckIn() != null && timetable.getCheckOut() != null) {
	                Duration duration = Duration.between(timetable.getCheckIn(), timetable.getCheckOut());
	                BigDecimal hours = BigDecimal.valueOf(duration.toMinutes()).divide(BigDecimal.valueOf(60));
	                hoursWorked = hoursWorked.add(hours);
	            }
            }

            BigDecimal moneyGenerated = BigDecimal.ZERO;
            LocalDateTime endOfMonth = LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.MAX);
            List<WorkOrder> workOrders = workOrderRepository.findByUserAndEndTimeStampBefore(user, endOfMonth);
            for (WorkOrder workOrder : workOrders) {
                moneyGenerated = moneyGenerated.add(workOrder.getPrice());
            }

            LocalDate localDate = yearMonth.atDay(1);
            Payroll payroll = new Payroll(user, localDate, hoursWorked, moneyGenerated);
            payrolls.add(payroll);
        }

        // Save the payrolls
        payrollRepository.saveAll(payrolls);
    }    
//    @Transactional
//    public void generatePayroll(YearMonth yearMonth) {
//        List<Payroll> payrolls = new ArrayList<>();
//
//        // Retrieve the users and their timetables for the specified month
//        List<Object[]> results = timetableRepository.findTimetablesAndUsersForMonth(yearMonth.getYear(),yearMonth.getMonthValue());
//
//        // Loop through the results, calculating the hours worked and money generated for each user
//        for (Object[] result : results) {
//            User user = (User) result[1];
//            Timetable timetable = (Timetable) result[0];
//
//            BigDecimal hoursWorked = BigDecimal.ZERO;
//            if (timetable.getCheckIn() != null && timetable.getCheckOut() != null) {
//                Duration duration = Duration.between(timetable.getCheckIn(), timetable.getCheckOut());
//                BigDecimal hours = BigDecimal.valueOf(duration.toMinutes()).divide(BigDecimal.valueOf(60));
//                hoursWorked = hoursWorked.add(hours);
//            }
//
//            BigDecimal moneyGenerated = BigDecimal.ZERO;
//            LocalDateTime endOfMonth = LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.MAX);
//            List<WorkOrder> workOrders = workOrderRepository.findByUserAndEndTimeStampBefore(user, endOfMonth);
//            for (WorkOrder workOrder : workOrders) {
//                moneyGenerated = moneyGenerated.add(workOrder.getPrice());
//            }
//
//            LocalDate localDate = yearMonth.atDay(1);
//            Payroll payroll = new Payroll(user, localDate, hoursWorked, moneyGenerated);
//            payrolls.add(payroll);
//        }
//
//        // Save the payrolls
//        payrollRepository.saveAll(payrolls);
//    }

//    public void generatePayroll(YearMonth yearMonth) {
//    	
//    	List<User> users = userRepository.findAll();
//        for (User user : users) {
//            BigDecimal hoursWorked = BigDecimal.ZERO;
//            List<Timetable> timetables = timetableRepository.findByUser(user);
//            for (Timetable timetable : timetables) {
//                LocalDate timetableDate = timetable.getDate();
//                if (timetableDate.getYear() == yearMonth.getYear() && timetableDate.getMonth() == yearMonth.getMonth()) {
//                    if (timetable.getCheckIn() != null && timetable.getCheckOut() != null) {
//                        Duration duration = Duration.between(timetable.getCheckIn(), timetable.getCheckOut());
//                        BigDecimal hours = BigDecimal.valueOf(duration.toMinutes()).divide(BigDecimal.valueOf(60));
//                        hoursWorked = hoursWorked.add(hours);
//                    }
//                }
//            }
//            // Calculate money generated from work orders that ended in this month
//            BigDecimal moneyGenerated = BigDecimal.ZERO;
//            LocalDateTime endOfMonth = LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.MAX);
//            List<WorkOrder> workOrders = workOrderRepository.findByUserAndEndTimeStampBefore(user, endOfMonth);
//            for (WorkOrder workOrder : workOrders) {
//                moneyGenerated = moneyGenerated.add(workOrder.getPrice());
//            }
//            // Save the payroll for this user and month
//            LocalDate localDate = yearMonth.atDay(1);
//            Payroll payroll = new Payroll(user, localDate, hoursWorked, moneyGenerated);
//            payrollRepository.save(payroll);
//        }
//    }
    
	public void updatePayroll(WorkOrder workOrder) {
		
		Payroll payroll = payrollRepository.findByUser(workOrder.getUser());
		payroll.addAmount(workOrder.getPrice());
		payrollRepository.save(payroll);
	}

}