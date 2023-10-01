package com.godel.employeemanagementrestful.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.godel.employeemanagementrestful.config.TestConfig;
import com.godel.employeemanagementrestful.enums.OfficeCode;
import com.godel.employeemanagementrestful.enums.Role;

@SpringBootTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class UserTest {
	

    @Test
    @DisplayName("Should create user correctly with Builder pattern")
    void testUserCreationWithBuilder() {
        User user = User.builder()
                .emailId("test@example.com")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .officeCode(OfficeCode.KRK) // Use one of the valid OfficeCode values
                .role(Role.Engineer) // Use one of the valid Role values
                .isEmployed(true)
                .build();

        assertEquals("test@example.com", user.getEmailId());
        assertEquals("password123", user.getPassword());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals(OfficeCode.KRK, user.getOfficeCode());
        assertEquals(Role.Engineer, user.getRole());
        assertTrue(user.getIsEmployed());
    }

}
