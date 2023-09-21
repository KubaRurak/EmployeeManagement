package com.godel.employeemanagementrestful.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.enums.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmailId(String emailId);

	User findWithWorkOrdersByUserId(Long userId);

    Long countByRoleNot(Role role);

}
