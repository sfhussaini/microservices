package com.example.training.manageemployee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component //Requesting Spring Core to create object of Employee Service
public class EmployeeService {
	@Autowired //Requesting Spring Core for the object of employee repository.
	EmployeeRepository repository;
	
	public boolean createEmployee(Employee newEmp) {
		return repository.createEmployee(newEmp);
	}
	
	public Employee findEmployeeById(Integer empId) {
		return repository.findEmployeeById(empId);
	}
}
