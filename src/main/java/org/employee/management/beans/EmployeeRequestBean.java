package org.employee.management.beans;

import java.util.List;

import org.employee.management.model.Employee;

import lombok.Data;


@Data
public class EmployeeRequestBean {
	List<Employee> employees;
}
