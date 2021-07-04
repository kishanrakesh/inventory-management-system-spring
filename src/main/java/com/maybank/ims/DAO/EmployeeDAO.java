package com.maybank.ims.DAO;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.maybank.ims.model.Employee;

public interface EmployeeDAO extends CrudRepository<Employee, Integer>{
	public List<Employee> findByEmployeeName(String employeeName);
}
