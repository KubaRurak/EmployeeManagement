package com.godel.employeemanagementrestful.initialize.generator;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;

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

        return User.builder()
            .firstName(firstName)
            .lastName(lastName)
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




