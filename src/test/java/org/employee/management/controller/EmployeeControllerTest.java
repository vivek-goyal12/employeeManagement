package org.employee.management.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.employee.management.beans.BonusEmployeeResponse;
import org.employee.management.beans.EmployeeRequestBean;
import org.employee.management.model.Employee;
import org.employee.management.service.EmployeeService;
import org.employee.management.util.LocalDateAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;
    
    
   

    @BeforeEach
    public void setUp() {
        // No need for MockitoAnnotations.openMocks(this) in @WebMvcTest
    }

    @Test
    public void testSaveEmployeeDetails() throws Exception {
    	
    	Gson gson = new GsonBuilder()
    	        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
    	        .create();

        EmployeeRequestBean employeeRequestBean = new EmployeeRequestBean();
        employeeRequestBean.setEmployees(Arrays.asList(
            new Employee("Raj Singh", "accounts", 5000.0, "INR", LocalDate.parse("May-20-2022", DateTimeFormatter.ofPattern("MMM-dd-yyyy")), 
                    LocalDate.parse("May-20-2023", DateTimeFormatter.ofPattern("MMM-dd-yyyy")))
        ));
        
        // Mock the service behavior
        when(employeeService.saveEmployeeDetails(employeeRequestBean.getEmployees())).thenReturn("Employee details have been successfully saved..");
        
//        // Act & Assert
        mockMvc.perform(post("/employee/addEmployeesDetails") 
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(employeeRequestBean))) 
                .andExpect(status().isOk())
                .andExpect(content().string("Employee details have been successfully saved.."))
                .andDo(print());

        // Verify the service method was called
        verify(employeeService, times(1)).saveEmployeeDetails(employeeRequestBean.getEmployees());
    }

    @Test
    void testGetEligibleEmployeeData_InvalidDateFormat() throws Exception {
        // Perform GET request with an invalid date format
        mockMvc.perform(get("/employee/getEligibleEmployeesForBonus")
                .param("date", "invalid-date")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void testGetEligibleEmployeeData_ValidDate() throws Exception {
        // Prepare mock data
        String validDate = "May-20-2022";
        List<Employee> employees = Arrays.asList(
                new Employee("raj singh", "accounts", 5000.0, "INR", LocalDate.now(), LocalDate.now()),
                new Employee("pratap m", "accounts", 3000.0, "INR", LocalDate.now(), LocalDate.now()),
                new Employee("sam", "operations", 2500.0, "USD", LocalDate.now(), LocalDate.now()),
                new Employee("susan", "operations", 700.0, "USD", LocalDate.now(), LocalDate.now())
        );

        // Mock the service call
        when(employeeService.findEligibleEmployees(LocalDate.parse(validDate, DateTimeFormatter.ofPattern("MMM-dd-yyyy"))))
                .thenReturn(employees);

        // Perform the GET request
        mockMvc.perform(get("/employee/getEligibleEmployeesForBonus")
                .param("date", validDate)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorMessage").value(""))
                .andDo(print());

        // Verify that the service method was called
        verify(employeeService).findEligibleEmployees(LocalDate.parse(validDate, DateTimeFormatter.ofPattern("MMM-dd-yyyy")));
    }

    @Test
    public void testGetEligibleEmployeeData() throws Exception {
        // Arrange
        String date = "May-20-2022";
        LocalDate requestDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
        List<Employee> eligibleEmployees = Arrays.asList(
            new Employee("Raj Singh", "accounts", 5000.0, "INR", LocalDate.now(), LocalDate.now()),
            new Employee("Sam", "operations", 2500.0, "USD", LocalDate.now(), LocalDate.now())
        );

        // Prepare the response structure
        BonusEmployeeResponse response = new BonusEmployeeResponse("", Arrays.asList(
            new BonusEmployeeResponse.CurrencyEmployees("INR", Arrays.asList(new BonusEmployeeResponse.CurrencyEmployees.EmployeeInfo("Raj Singh", 5000.0))),
            new BonusEmployeeResponse.CurrencyEmployees("USD", Arrays.asList(new BonusEmployeeResponse.CurrencyEmployees.EmployeeInfo("Sam", 2500.0)))
        ));

        // Mock the service call
        when(employeeService.findEligibleEmployees(requestDate)).thenReturn(eligibleEmployees);

        // Act & Assert
        mockMvc.perform(get("/employee/getEligibleEmployeesForBonus") // Use the appropriate endpoint
                .param("date", date)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errorMessage").value(""))
                .andDo(print());

        // Verify that the service method was called
        verify(employeeService, times(1)).findEligibleEmployees(requestDate);
    }
}
