package com.godel.employeemanagementrestful.initialize.generate;

import java.util.Random;

import com.godel.employeemanagementrestful.entity.User;
import com.godel.employeemanagementrestful.enums.OfficeCode;
import com.godel.employeemanagementrestful.enums.UserRole;

import org.apache.commons.lang3.StringUtils;

public class UserGenerator {
	private static final String[] GENDER = {"Male", "Female"};
    private static final String[] FIRST_NAMES_MALE = {"Norbert", "Alojzy", "Michał", "Daniel",
    		"Paweł", "Fabian","Amadeusz","Joachim","Mariusz","Korneliusz",
    		"Juliusz","Dorian","Martin","Kornel","Jakub", "Julisz", "Dorian"};
    private static final String[] LAST_NAMES_MALE = {"Zawadzki", "Sadowski", "Kozłowski",
    		"Gajewski", "Kucharski", "Mróz", "Szczepanski", "Kazmierczak", "Cieślak", "Czerwiński", "Kowalczyk",
    		"Kubiak","Wójcik","Głowacki","Kalinowski","Sikorka","Kaczmarczyk","Kozłowiski"};
    private static final String[] FIRST_NAMES_FEMALE = {"Halina","Katarzyna","Martyna","Agata","Anna","Amelia","Agnieszka",
    		"Magda","Zuzanna","Dorota","Joanna","Lilia","Wiesława","Grażyna","Danuta","Iwona"};
    private static final String[] LAST_NAMES_FEMALE = {"Zawadzka", "Sadowska", "Kozłowska",
    		"Gajewska", "Kucharska", "Mróz", "Szczepanska", "Kazmierczak", "Cieślak", "Czerwińska", "Kowalczyk",
    		"Kubiak","Wójcik","Głowacka","Kalinowska","Sikorka","Kaczmarczyk","Kozłowiski"};    
    private static final Random RANDOM = new Random();
    
    public static String generateEmail(String firstName, String lastName) {
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.stripAccents(firstName.toLowerCase()));
        sb.append(StringUtils.stripAccents(lastName.toLowerCase()));
        sb.append("@companyname.com");
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
        UserRole role = UserRole.Engineer;

        return User.builder()
            .firstName(firstName)
            .lastName(lastName)
            .emailId(email)
            .role(role)
            .officeCode(officeCode)
            .isEmployed(true)
            .build();
    }
}




