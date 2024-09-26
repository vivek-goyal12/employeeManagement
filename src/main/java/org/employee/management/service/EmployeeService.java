package org.employee.management.service;

import org.apache.coyote.BadRequestException;
import org.employee.management.model.Employee;
import org.employee.management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository empRepository;

	public String saveEmployeeDetails(List<Employee> employeeRequest) throws BadRequestException {
// check for already exists data
		checkEmployees(employeeRequest);
		// Save all the filtered employees
		if (!empRepository.saveAll(employeeRequest).isEmpty())
			return "Employee details has been successfully saved..";
		else
			throw new BadRequestException("Error in saving employees details");

	}

	public List<Employee> findEligibleEmployees(LocalDate date) {
		return empRepository.findByJoiningDateLessThanEqualAndExitDateGreaterThanEqual(date, date);
	}
	
	public void checkEmployees(List<Employee> employees) throws BadRequestException {
		// Find the employees that already exist in the database
		List<Employee> existingEmployees = employees.stream()
				.filter(emp -> {
					// Check if the employee already exists
					List<Employee> foundEmployees = empRepository.findByEmpNameAndDepartmentAndJoiningDate(
							emp.getEmpName(),
							emp.getDepartment(),
							emp.getJoiningDate()
					);

					return !foundEmployees.isEmpty();
				})
				.collect(Collectors.toList());
//		validate already exists employees
		if (!existingEmployees.isEmpty()) {
			throw new BadRequestException("The following employees already exist: " + existingEmployees.get(0).getEmpName());
		}
	}
}
