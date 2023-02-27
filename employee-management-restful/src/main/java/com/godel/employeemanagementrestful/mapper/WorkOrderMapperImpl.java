package com.godel.employeemanagementrestful.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.godel.employeemanagementrestful.dto.WorkOrderDTO;
import com.godel.employeemanagementrestful.entity.Customer;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;

@Component
public class WorkOrderMapperImpl implements Mapper<WorkOrder, WorkOrderDTO> {
	
	private ModelMapper modelMapper;

	public WorkOrderMapperImpl(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	
	@Override
	public WorkOrderDTO mapperEntityToDto(WorkOrder workOrder) {
		WorkOrderDTO workOrderDTO = modelMapper.map(workOrder, WorkOrderDTO.class);
		return workOrderDTO;

	}

	@Override
	public WorkOrder mapperDtoToEntity(WorkOrderDTO workOrderDTO) {

	    WorkOrder workOrder = modelMapper.map(workOrderDTO, WorkOrder.class);

	    // Set the user for the work order
	    if (workOrderDTO.getUserId() != null) {
	        User user = new User();
	        user.setUserId(workOrderDTO.getUserId());
	        workOrder.setUser(user);
	    }

	    // Set the customer for the work order
	    if (workOrderDTO.getCustomerId() != null) {
	        Customer customer = new Customer();
	        customer.setCustomerId(workOrderDTO.getCustomerId());
	        workOrder.setCustomer(customer);
	    }

	    return workOrder;
        		
	}

}
