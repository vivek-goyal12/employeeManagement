package org.employee.management.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.employee.management.model.Employee;
import org.employee.management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository empRepository;

	public String saveEmployeeDetails(List<Employee> employeeRequest) throws BadRequestException {
// check for already exists data
		checkEmployees(employeeRequest);
		empRepository.findByEmpNameAndDepartmentAndJoiningDate(null, null, null);
		empRepository.saveAll(employeeRequest);
		return "Employee details has been sucessfully saved..";
	}

	public List<Employee> findEligibleEmployees(LocalDate date) {
		return empRepository.findByJoiningDateLessThanEqualAndExitDateGreaterThanEqual(date, date);
	}
	
	public void checkEmployees(List<Employee> employeeRequest) throws BadRequestException {
	    // Stream the employeeRequest list
	    Optional<Employee> matchingEmployee = employeeRequest.stream()
	        .filter(employee -> empRepository.findByEmpNameAndDepartmentAndJoiningDate(
	            employee.getEmpName(), employee.getDepartment(), employee.getJoiningDate()
	        ) != null) // Adjust this condition based on your repository method's return type
	        .findFirst(); // Get the first matching employee, if any

	    // Check if a matching employee was found
	    if (matchingEmployee.isPresent()) {
	        System.out.println("Matching employee found: " + matchingEmployee.get());
//	        throw new BadRequestException(matchingEmployee.get().getEmpName() + " is already registered");
	    } else {
	        System.out.println("No matching employee found.");
	    }
	}
}
