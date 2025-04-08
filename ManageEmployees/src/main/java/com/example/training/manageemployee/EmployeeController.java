package com.example.training.manageemployee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
	@Autowired
	EmployeeService businessLogic;
	
	@PostMapping("/employees") //http://localhost:8080/employees
	public ResponseEntity createEmployee(@RequestBody Employee emp) {
		boolean isCreated = businessLogic.createEmployee(emp);
		if(isCreated) {
			//Returning Status code 201 and String message
			ResponseEntity response = ResponseEntity.status(HttpStatus.CREATED).body("Employee Created");
			return response;
		}else {
			//Returning Status code 400 and String message
			ResponseEntity response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee creation failed");
			return response;			
		}
	}

	//Invoke getEmployeeById(100) method via URL http://localhost:port/employees/100
	@RequestMapping(path = "/employees/{paramEmpId}", method = RequestMethod.GET)
	public ResponseEntity getEmployeeById(@PathVariable("paramEmpId") int empId) {
		Employee emp = businessLogic.findEmployeeById(empId);
		if(emp != null) {
			//Returning Status code 200 and employee JSON
			ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(emp);
			return response;
		}else {
			//Returning Status code 404 and String message
			ResponseEntity response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee with given id not found");
			return response;
		}			
	}
}
