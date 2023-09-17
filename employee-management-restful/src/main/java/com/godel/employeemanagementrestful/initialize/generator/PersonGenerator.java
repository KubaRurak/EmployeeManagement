package com.godel.employeemanagementrestful.initialize.generator;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.godel.employeemanagementrestful.entity.Customer;
import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.enums.OfficeCode;
import com.godel.employeemanagementrestful.enums.Role;

public class PersonGenerator {
	private static final String[] GENDER = {"Male", "Female"};
    private static final String[] FIRST_NAMES_MALE = {"Norbert", "Alojzy", "Michał", "Daniel",
    		"Paweł", "Fabian","Amadeusz","Joachim","Mariusz","Korneliusz",
    		"Juliusz","Dorian","Martin","Kornel","Jakub", "Juliusz", "Dorian",
    		"Alan", "Robert", "Mateusz", "Piotr", "Norbert", "Roman", "Ryszard"};
    private static final String[] LAST_NAMES_MALE = {"Zawadzki", "Sadowski", "Kozłowski",
    		"Gajewski", "Kucharski", "Mróz", "Szczepanski", "Kazmierczak", "Cieślak", "Czerwiński", "Kowalczyk",
    		"Kubiak","Wójcik","Głowacki","Kalinowski","Sikorka","Kaczmarczyk","Kozłowski","Markowski","Zieliński","Kłos"};
    private static final String[] FIRST_NAMES_FEMALE = {"Halina","Katarzyna","Martyna","Agata","Anna","Amelia","Agnieszka",
    		"Magda","Zuzanna","Dorota","Joanna","Lilia","Wiesława","Grażyna","Danuta","Iwona","Malina","Aleksandra","Paulina","Paula"};
    private static final String[] LAST_NAMES_FEMALE = {"Zawadzka", "Sadowska", "Kozłowska",
    		"Gajewska", "Kucharska", "Mróz", "Szczepanska", "Kazmierczak", "Cieślak", "Czerwińska", "Kowalczyk",
    		"Kubiak","Wójcik","Głowacka","Kalinowska","Sikorka","Kaczmarczyk","Kozłowiski"};  
    private static final String[] COMPANY_NAMES = {"Company A", "Company B", "Company C", "Company D", "Company E", "Company F", "Company G"};
    private static final Random RANDOM = new Random();
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String generateEmail(String firstName, String lastName) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.stripAccents(firstName.toLowerCase()));
        sb.append(StringUtils.stripAccents(lastName.toLowerCase()));
        sb.append("@yourcompany.com");
        return sb.toString();
      }
    public static String generateGender() {
        int index = RANDOM.nextInt(GENDER.length);
        return GENDER[index];
    }

    public static User generateUser() {
    	String gender = generateGender();
    	String firstName = (gender.equals("Male")) ? 
                FIRST_NAMES_MALE[RANDOM.nextInt(FIRST_NAMES_MALE.length)] : 
                FIRST_NAMES_FEMALE[RANDOM.nextInt(FIRST_NAMES_FEMALE.length)];
    	String lastName = (gender.equals("Male")) ? 
               LAST_NAMES_MALE[RANDOM.nextInt(LAST_NAMES_MALE.length)] : 
               LAST_NAMES_FEMALE[RANDOM.nextInt(LAST_NAMES_FEMALE.length)];
        String email = generateEmail(firstName, lastName);
        OfficeCode officeCode = OfficeCode.values()[RANDOM.nextInt(OfficeCode.values().length)];
        Role role = Role.Engineer;
        String defaultPassword;
        switch (role) {
            case Engineer: defaultPassword = "engineerPass"; break;
            case Admin: defaultPassword = "adminPass"; break;
            case Operator: defaultPassword = "operatorPass"; break;
            default: defaultPassword = "defaultPass";
        }
        String encodedPassword = passwordEncoder.encode(defaultPassword);


        return User.builder()
            .firstName(firstName)
            .lastName(lastName)
            .password(encodedPassword)
            .emailId(email)
            .role(role)
            .officeCode(officeCode)
            .isEmployed(true)
            .build();
    }
    
    public static User generateSpecificUser(Role role) {
        String firstName;
        String lastName;
        String email;
        String password;
        //for showcase purposes
        switch (role) {
            case Admin:
                firstName = "Admin";
                lastName = "User";
                email = "admin@yourcompany.com";
                password = "adminPass";
                break;
            case Operator:
                firstName = "Operator";
                lastName = "User";
                email = "operator@yourcompany.com";
                password = "operatorPass";
                break;
            case Engineer:
            default:
                firstName = "Engineer";
                lastName = "User";
                email = "engineer@yourcompany.com";
                password = "engineerPass";
        }
        String encodedPassword = passwordEncoder.encode(password);


        OfficeCode officeCode = OfficeCode.values()[RANDOM.nextInt(OfficeCode.values().length)];

        return User.builder()
            .firstName(firstName)
            .lastName(lastName)
            .password(encodedPassword)
            .emailId(email)
            .role(role)
            .officeCode(officeCode)
            .isEmployed(true)
            .build();
    }
    
    
    
    public static Customer generateCustomer() {
    	String gender = generateGender();
    	String firstName = (gender.equals("Male")) ? 
                FIRST_NAMES_MALE[RANDOM.nextInt(FIRST_NAMES_MALE.length)] : 
                FIRST_NAMES_FEMALE[RANDOM.nextInt(FIRST_NAMES_FEMALE.length)];
    	String lastName = (gender.equals("Male")) ? 
               LAST_NAMES_MALE[RANDOM.nextInt(LAST_NAMES_MALE.length)] : 
               LAST_NAMES_FEMALE[RANDOM.nextInt(LAST_NAMES_FEMALE.length)];
        String email = generateEmail(firstName, lastName);
        String companyName = COMPANY_NAMES[RANDOM.nextInt(COMPANY_NAMES.length)];

        
        return Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .emailId(email) 
                .companyName(companyName)
                .build();
    
    }
  
    
}




