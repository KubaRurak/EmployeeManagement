package com.godel.employeemanagementrestful.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            BigDecimal timeWorked = BigDecimal.ZERO;
            
            List<Timetable> timetables = timetableRepository.findByUserAndDateBetween(user, yearMonth.atDay(1), yearMonth.atEndOfMonth());
            for (Timetable timetable : timetables) {
	            if (timetable.getCheckIn() != null && timetable.getCheckOut() != null) {
	                Duration duration = Duration.between(timetable.getCheckIn(), timetable.getCheckOut());
	                BigDecimal hours = BigDecimal.valueOf(duration.toMinutes()).divide(BigDecimal.valueOf(60));
	                timeWorked = timeWorked.add(hours);
	            }
            }

            BigDecimal moneyGenerated = BigDecimal.ZERO;
            LocalDateTime endOfMonth = LocalDateTime.of(yearMonth.atEndOfMonth(), LocalTime.MAX);
            List<WorkOrder> workOrders = workOrderRepository.findByUserAndEndTimeStampBefore(user, endOfMonth);
            for (WorkOrder workOrder : workOrders) {
                moneyGenerated = moneyGenerated.add(workOrder.getPrice());
            }

            LocalDate localDate = yearMonth.atDay(1);
            Payroll existingPayroll = payrollRepository.findByUserAndPayrollMonth(user, localDate);
            if (existingPayroll != null) {
                existingPayroll.setTimeWorked(timeWorked);
                existingPayroll.setMoneyGenerated(moneyGenerated);
                payrolls.add(existingPayroll);
            } else {
                Payroll payroll = new Payroll(user, localDate, timeWorked, moneyGenerated);
                payrolls.add(payroll);
            }
        }

        // Save the payrolls
        payrollRepository.saveAll(payrolls);
    }    

    
	public void updatePayrollMoney(WorkOrder workOrder) {
		
		Payroll payroll = payrollRepository.findByUser(workOrder.getUser());
		payroll.addAmount(workOrder.getPrice());
		payrollRepository.save(payroll);
	}
	
	public void updatePayrollTime(Long userId, Timetable timetable) {
		LocalDate date = LocalDate.now().withDayOfMonth(1);
	    List<Payroll> payrolls = payrollRepository.findByUserUserIdAndPayrollMonth(userId, date);
	    BigDecimal workedTime = BigDecimal.valueOf(timetable.getTimeWorked().getSeconds())
	    	    .divide(BigDecimal.valueOf(3600), 2, RoundingMode.HALF_UP);
	    Payroll payroll=payrolls.get(0);
	    payroll.addTimeWorked(workedTime);
	    payrollRepository.save(payroll);
	}
	
    public List<Payroll> getFilteredPayroll(Long userId, LocalDate payrollMonth) {
        if (userId == null && payrollMonth == null) {
            return payrollRepository.findAll();
        }
        if (userId == null) {
            return payrollRepository.findByPayrollMonth(payrollMonth);
        }
        if (payrollMonth== null) {
            return payrollRepository.findByUserUserId(userId);
        }
        return payrollRepository.findByUserUserIdAndPayrollMonth(userId,payrollMonth);
    }

}