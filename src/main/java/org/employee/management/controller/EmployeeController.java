package org.employee.management.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.employee.management.beans.BonusEmployeeResponse;
import org.employee.management.beans.BonusEmployeeResponse.CurrencyEmployees.EmployeeInfo;
import org.employee.management.beans.EmployeeRequestBean;
import org.employee.management.model.Employee;
import org.employee.management.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/addEmployeesDetails")
	public ResponseEntity<String> saveEmployeeDetails(@RequestBody EmployeeRequestBean employeeRequest) {
//		Save Employee Details into DB
		String response;
		try {
			 response = employeeService.saveEmployeeDetails(employeeRequest.getEmployees());
		} catch (BadRequestException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("internal server error : "+ e.getMessage());
		}
//		Return the response
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getEligibleEmployeesForBonus")
	public ResponseEntity<BonusEmployeeResponse> getEligibleEmployeeData(@RequestParam(name="date") String date) {
		BonusEmployeeResponse response ;
	   try { // Parse the request date
	    LocalDate requestDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("MMM-dd-yyyy"));
	    
	    // Fetch the eligible employees
	    List<Employee> eligibleEmployees = employeeService.findEligibleEmployees(requestDate);

	    // Group employees by currency
	    Map<String, List<EmployeeInfo>> groupedEmployees = eligibleEmployees.stream()
	        .collect(Collectors.groupingBy(Employee::getCurrency, 
	                 Collectors.mapping(emp -> new EmployeeInfo(emp.getEmpName(), emp.getAmount()), 
	                                    Collectors.toList())));

	    // Prepare the response structure
	    List<BonusEmployeeResponse.CurrencyEmployees> responseData = groupedEmployees.entrySet().stream()
	        .map(entry -> new BonusEmployeeResponse.CurrencyEmployees(entry.getKey(), entry.getValue()))
	        .collect(Collectors.toList());
	    
	    // Return the response
	    response =  new BonusEmployeeResponse("", responseData);
	    return ResponseEntity.ok(response);
	   } catch(DateTimeParseException e) {
		   return ResponseEntity.badRequest().body(new BonusEmployeeResponse(e.getMessage(), null));
	   } catch(Exception e) {
		   e.printStackTrace();
		   return ResponseEntity.internalServerError().body(new BonusEmployeeResponse("internal server error", null));   
	   }

	}

}
