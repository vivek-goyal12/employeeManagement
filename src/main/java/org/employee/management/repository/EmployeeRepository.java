package org.employee.management.repository;

import java.time.LocalDate;
import java.util.List;

import org.employee.management.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByJoiningDateLessThanEqualAndExitDateGreaterThanEqual(LocalDate date, LocalDate date2);

}
