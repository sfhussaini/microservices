package com.example.training.manageemployee;

import static org.assertj.core.api.Assertions.assertThatException;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ManageEmployeeApplicationTests {
    @LocalServerPort
    Integer serverPort; //Server port will be injected here during Testcase execution.
	
	@Test
	void contextLoads() {
	}

	@Test
	void testQueryEmployee() {
		RestTemplate template = new RestTemplate();
		//Invoke http://localhost:8080/employees/100 using GET
		ResponseEntity<Employee> response = template.getForEntity("http://localhost:"+serverPort+"/employees/100", Employee.class);
		//Checking if the response object name is 'James Cooper' ==> If yes, then case is passed.
		assertEquals(response.getBody().getEmpName(), "James Cooper");		
	} 

	@Test
	void testQueryEmployeeNotFound() {
		RestTemplate template = new RestTemplate();
		//Invoke http://localhost:8080/employees/100 using GET
		ResponseEntity<Employee> response = null;
		try {
			response = template.getForEntity("http://localhost:"+serverPort+"/employees/500", Employee.class);
		}catch(Exception e) {
			assertEquals(e.getMessage(), "404 : \"Employee with given id not found\"");
		}
	} 
}
