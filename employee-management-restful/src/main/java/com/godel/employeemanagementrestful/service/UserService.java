package com.godel.employeemanagementrestful.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.godel.employeemanagementrestful.dto.UserDTO;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.entity.WorkOrder;
import com.godel.employeemanagementrestful.repository.UserRepository;

@Service
public class UserService {
	
//    public UserWithWorkOrdersDTO convertToDTO(User user) {
//        UserWithWorkOrdersDTO dto = new UserWithWorkOrdersDTO();
//        dto.setUserId(user.getUserId());
//        dto.setEmailId(user.getEmailId());
//        dto.setFirstName(user.getFirstName());
//        dto.setLastName(user.getLastName());
//        List<Long> activeWorkOrderIds = new ArrayList<>();
//
//        return dto;
//    }
	
//	@Autowired
//	private UserRepository userRepository;
//
//	@Override
//	public User saveUser(User user) {
//		return userRepository.save(user);
//	}
//
//	@Override
//	public List<User> fetchUserList() {
//		return userRepository.findAll();
//	}
//	
//	@Override
//	public Optional<User> fetchUserById(Long id) {
//		return userRepository.findById(id);
//	}
	

}
