package com.maybank.ims.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.maybank.ims.DAO.EmployeeDAO;
import com.maybank.ims.model.Employee;

@RestController
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	EmployeeDAO employeeDAO;
	
	@Override
	public boolean addEmployee(Employee employee) {
		employeeDAO.save(employee);
		return true;
	}

	@Override
	public boolean updateEmployee(Employee employee) {
		employeeDAO.save(employee);
		return true;
	}

	@Override
	public boolean deleteEmployee(int employeeId) {
		employeeDAO.deleteById(employeeId);
		return true;
	}

	@Override
	public List<Employee> viewEmployees() {
		return (List<Employee>) employeeDAO.findAll();
	}

	@Override
	public Employee searchEmployeeById(int employeeId) {
		return employeeDAO.findById(employeeId).get();
	}

	@Override
	public List<Employee> searchEmployeeByName(String employeeName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmployeeExists(int employeeId) {
		return employeeDAO.existsById(employeeId);
	}
}
	


