package com.maybank.ims.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maybank.ims.model.Employee;
import com.maybank.ims.service.EmployeeService;

@RestController
@RequestMapping("employees")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping
	public ResponseEntity<List<Employee>> getEmployees() {
		List<Employee> employees = (List<Employee>) employeeService.viewEmployees();
		return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> add(@RequestBody Employee employee) {
		
		ResponseEntity<String> responseEntity = null;
		
		if (employeeService.isEmployeeExists(employee.getEmployeeId())) 
			responseEntity = new ResponseEntity<String> ("Employee Already exists ",HttpStatus.CONFLICT);
			
		
		else {
			
			if(employeeService.addEmployee(employee))
				responseEntity = new ResponseEntity<String> ("Added a Employee " +employee,HttpStatus.CREATED);
				
			else {
					responseEntity = new ResponseEntity<String> ("Please fill the right details",HttpStatus.NOT_ACCEPTABLE);
				}
		}
		
		return responseEntity;
	}
	@PutMapping
	public ResponseEntity<String> update(@RequestBody Employee employee) {
		int employeeId = employee.getEmployeeId();
		ResponseEntity<String> responseEntity = null;
		if (employeeService.isEmployeeExists(employeeId))
		{
			employeeService.addEmployee(employee);
			responseEntity = new ResponseEntity<String>("Employee updated successfully", HttpStatus.OK);	
		}
			else
			responseEntity = new ResponseEntity<String>("Employee does not exist", HttpStatus.NO_CONTENT);
		return responseEntity;
	}
	
	@DeleteMapping("{employeeId}")
	public ResponseEntity<String> delete(@PathVariable("employeeId")int employeeId) {
		ResponseEntity<String> responseEntity = null;
		if (employeeService.isEmployeeExists(employeeId))
		{
			Employee employee=employeeService.searchEmployeeById(employeeId);
			employeeService.deleteEmployee(employeeId);
			responseEntity = new ResponseEntity<String>("Employee deleted successfully:" + employee, HttpStatus.OK);
			
		}
		else {
			responseEntity = new ResponseEntity<String>("Employee does not exist", HttpStatus.NO_CONTENT);
		}
		return responseEntity;
	}
	
	
	@GetMapping("employeeId/{employeeId}")
	public ResponseEntity<Employee> searchEmployee(@PathVariable("employeeId")int employeeId) 
	{
		ResponseEntity<Employee> responseEntity = null;
		Employee foundEmployee = new Employee();
		if (employeeService.isEmployeeExists(employeeId)) {
			Employee employee = (Employee)employeeService.searchEmployeeById(employeeId);
			foundEmployee = employee;
			responseEntity = new ResponseEntity<Employee>(foundEmployee, HttpStatus.OK);
		}
		else
			responseEntity = new ResponseEntity<Employee>(foundEmployee, HttpStatus.NO_CONTENT);
		return responseEntity;
	}
	
	@GetMapping("{employeeName}")
	public ResponseEntity<List<Employee>> searchEmployeeByName(@PathVariable("employeeName")String employeeName) 
	{
		List<Employee> searchedEmployees = new ArrayList<Employee>();
		searchedEmployees = employeeService.searchEmployeeByName(employeeName);
		ResponseEntity<List<Employee>> responseEntity = null;
		if (searchedEmployees.size() == 0) {
			responseEntity = new ResponseEntity<List<Employee>>(searchedEmployees,HttpStatus.NO_CONTENT);
		}
		else
			responseEntity = new ResponseEntity<List<Employee>>(searchedEmployees,HttpStatus.OK);
		return responseEntity;
	}
	
}
