package org.employee.management.model;

import java.time.LocalDate;

import org.employee.management.util.CustomLocalDateDeserializer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name="employee")
@Data
@AllArgsConstructor
public class Employee {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	private String empName;
	private String department;
	private Double amount;
	private String currency;
	
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM-dd-yyyy")
    
	private LocalDate joiningDate;
    @JsonDeserialize(using = CustomLocalDateDeserializer.class)	
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MMM-dd-yyyy")
	private LocalDate exitDate;
	
	public Employee() {
		
	}

	public Employee(String empName, Double amount) {
		this.empName=empName;
		this.amount=amount;
	}


	public Employee(String empName, String department, Double amount, String currency, LocalDate joiningDate, LocalDate exitDate) {
		this.empName=empName;
		this.department=department;
		this.amount=amount;
		this.currency=currency;
		this.joiningDate=joiningDate;
		this.exitDate=exitDate;
	}

	public Employee(String empName, Double amount, String currency) {
		this.empName=empName;
		this.amount=amount;
		this.currency=currency;
	}
}