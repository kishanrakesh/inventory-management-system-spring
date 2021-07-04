package com.maybank.ims.service;

import java.util.List;

import com.maybank.ims.model.Employee;

public interface EmployeeService {
	public boolean addEmployee(Employee employee);
	public boolean updateEmployee(Employee employee);
	public boolean deleteEmployee(int employeeId);
	public List<Employee> viewEmployees();
	public Employee searchEmployeeById(int employeeId);
	public List<Employee> searchEmployeeByName(String employeeName);
	boolean isEmployeeExists(int employeeId);
	
}
