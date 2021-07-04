package com.maybank.ims.controller;


import java.time.LocalDate;
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
import com.maybank.ims.model.Item;
import com.maybank.ims.service.EmployeeService;
import com.maybank.ims.service.ItemService;

@RestController
@RequestMapping("items")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("all")
	public ResponseEntity<List<Item>> getItems() {
		List<Item> items = (List<Item>) itemService.viewItems();
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Item>> getUnassignedItems() {
		List<Item> items = (List<Item>) itemService.viewUnassignedItems();
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> add(@RequestBody Item item) {
		
		//Employee employee = employeeService.searchEmployeeById(employeeId);
		//item.setEmployee(employee);
		ResponseEntity<String> responseEntity = null;
		
		if (itemService.isItemExists(item.getItemId())) 
			responseEntity = new ResponseEntity<String> ("Item Already exists ",HttpStatus.CONFLICT);
			
		
		else {
			
			if(itemService.addItem(item))
				responseEntity = new ResponseEntity<String> ("Added a Item " +item,HttpStatus.CREATED);
				
			else {
					responseEntity = new ResponseEntity<String> ("Please fill the right details",HttpStatus.NOT_ACCEPTABLE);
				}
		}
		
		return responseEntity;
	}
	
	
	/*
	@PostMapping("employeeId/{employeeId}")
	public ResponseEntity<String> addToEmployee(@PathVariable("employeeId")int employeeId, @RequestBody Item item) {
		
		Employee employee = employeeService.searchEmployeeById(employeeId);
		item.setEmployee(employee);
		ResponseEntity<String> responseEntity = null;
		
		if (itemService.isItemExists(item.getItemId())) 
			responseEntity = new ResponseEntity<String> ("Item Already exists ",HttpStatus.CONFLICT);
			
		
		else {
			
			if(itemService.addItem(item))
				responseEntity = new ResponseEntity<String> ("Added a Item " +item,HttpStatus.CREATED);
				
			else {
					responseEntity = new ResponseEntity<String> ("Please fill the right details",HttpStatus.NOT_ACCEPTABLE);
				}
		}
		
		return responseEntity;
	}
	
	*/
	
	@PutMapping
	public ResponseEntity<String> update(@RequestBody Item item) {
		int itemId = item.getItemId();
		ResponseEntity<String> responseEntity = null;
		if (itemService.isItemExists(itemId))
		{
			itemService.addItem(item);
			responseEntity = new ResponseEntity<String>("Item updated successfully", HttpStatus.OK);	
		}
		else
			responseEntity = new ResponseEntity<String>("Item does not exist", HttpStatus.NO_CONTENT);
		return responseEntity;
	}
	
	@PutMapping("{itemId}/assignTo/{employeeId}")
	public ResponseEntity<String> assignToEmployee(@PathVariable("employeeId")int employeeId,@PathVariable("itemId")int itemId) {
		ResponseEntity<String> responseEntity = null;
		if (itemService.isItemExists(itemId)) {
			if (employeeService.isEmployeeExists(employeeId)) {
				Item item = itemService.searchItemById(itemId);
				item.setEmployee(employeeService.searchEmployeeById(employeeId));
				item.setDate(java.time.LocalDate.now());
				itemService.addItem(item);
				responseEntity = new ResponseEntity<String>("Item Assigned Successfully", HttpStatus.OK);
			}
		}
				
		else
			responseEntity = new ResponseEntity<String>("Item does not exist", HttpStatus.NO_CONTENT);
		return responseEntity;
	}
	
	@PutMapping("{itemId}/assignTo/{employeeId}/{date}")
	public ResponseEntity<String> assignedOnDate(@PathVariable("employeeId")int employeeId,@PathVariable("itemId")int itemId,@PathVariable("date")String date) {
		ResponseEntity<String> responseEntity = null;
		if (itemService.isItemExists(itemId)) {
			if (employeeService.isEmployeeExists(employeeId)) {
				Item item = itemService.searchItemById(itemId);
				item.setEmployee(employeeService.searchEmployeeById(employeeId));
				LocalDate localDate = LocalDate.parse(date);
				item.setDate(localDate);
				itemService.addItem(item);
				responseEntity = new ResponseEntity<String>("Item Assigned Successfully", HttpStatus.OK);
			}
		}
				
		else
			responseEntity = new ResponseEntity<String>("Item does not exist", HttpStatus.NO_CONTENT);
		return responseEntity;
	}
	
	
	
	@PutMapping("{itemId}/unassignFrom/{employeeId}")
	public ResponseEntity<String> unassignFromEmployee(@PathVariable("employeeId")int employeeId,@PathVariable("itemId")int itemId) {
		ResponseEntity<String> responseEntity = null;
		if (itemService.isItemExists(itemId)) {
			if (employeeService.isEmployeeExists(employeeId)) {
				Item item = itemService.searchItemById(itemId);
				if (item.getEmployee()==employeeService.searchEmployeeById(employeeId))
				{
					item.setEmployee(null);
					item.setDate(null);
					itemService.addItem(item);
					responseEntity = new ResponseEntity<String>("Item Unassigned Successfully", HttpStatus.OK);
				}
				else
					responseEntity = new ResponseEntity<String>("Item was not Assigned to Employee", HttpStatus.CONFLICT);
				
			}
			else

				responseEntity = new ResponseEntity<String>("Employee does not exist", HttpStatus.NO_CONTENT);
		}
				
		else
			responseEntity = new ResponseEntity<String>("Item does not exist", HttpStatus.NO_CONTENT);
		return responseEntity;
	}
	
	@DeleteMapping("{itemId}")
	public ResponseEntity<String> delete(@PathVariable("itemId")int itemId) {
		ResponseEntity<String> responseEntity = null;
		if (itemService.isItemExists(itemId))
		{
			Item item=itemService.searchItemById(itemId);
			itemService.deleteItem(itemId);
			responseEntity = new ResponseEntity<String>("Item deleted successfully:" + item, HttpStatus.OK);
			
		}
		else {
			responseEntity = new ResponseEntity<String>("Item does not exist", HttpStatus.NO_CONTENT);
		}
		return responseEntity;
	}
	
	
	@GetMapping("itemId/{itemId}")
	public ResponseEntity<Item> searchItem(@PathVariable("itemId")int itemId) 
	{
		ResponseEntity<Item> responseEntity = null;
		Item foundItem = new Item();
		if (itemService.isItemExists(itemId)) {
			Item item = (Item)itemService.searchItemById(itemId);
			foundItem = item;
			responseEntity = new ResponseEntity<Item>(foundItem, HttpStatus.OK);
		}
		else
			responseEntity = new ResponseEntity<Item>(foundItem, HttpStatus.NO_CONTENT);
		return responseEntity;
	}
	
	@GetMapping("{itemName}")
	public ResponseEntity<List<Item>> searchItemByName(@PathVariable("itemName")String itemName) 
	{
		List<Item> searchedItems = new ArrayList<Item>();
		searchedItems = itemService.searchItemByName(itemName);
		ResponseEntity<List<Item>> responseEntity = null;
		if (searchedItems.size() == 0) {
			responseEntity = new ResponseEntity<List<Item>>(searchedItems,HttpStatus.NO_CONTENT);
		}
		else
			responseEntity = new ResponseEntity<List<Item>>(searchedItems,HttpStatus.OK);
		return responseEntity;
	}
	
}
