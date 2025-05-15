package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/")
public class GreetingController {
    private final RestTemplate restTemplate;
    
    // Assuming the inventory service URL is configured in application.properties
    @Value("${manageemployees.service.url}")
    private String manageEmployeesURL;

    @Autowired
    public GreetingController() {
        this.restTemplate = new RestTemplate();
    }
    
    @GetMapping("/greet")
    public String getEmployeeById(@RequestParam("id") String id) {
        // Construct the URL for the inventory service
        String url = manageEmployeesURL + "/employees/" + id;
        
        // Make the request to the inventory service
        ResponseEntity<Employee> response = restTemplate.getForEntity(url, Employee.class);
        
        // You might want to process the response before returning it
        String responseStr = "Greetings! " + response.getBody().getEmpName();
        return responseStr;
    }
}
