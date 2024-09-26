package org.employee.management.service;

import java.time.LocalDate;
import java.util.List;

import org.employee.management.model.Employee;
import org.employee.management.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository empRepository;

	public String saveEmployeeDetails(List<Employee> employeeRequest) {

		empRepository.saveAll(employeeRequest);
		return "Employee details has been sucessfully saved..";
	}

	public List<Employee> findEligibleEmployees(LocalDate date) {
		return empRepository.findByJoiningDateLessThanEqualAndExitDateGreaterThanEqual(date, date);
	}
}
