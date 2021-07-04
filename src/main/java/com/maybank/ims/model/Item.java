package com.maybank.ims.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int itemId;
	
	@Column(nullable = false)
	private String itemName;
	
	@Column
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name="employee_id")
	@JsonIgnore
	Employee employee;
	
	public Item() {
		super();
	}

	public Item(int itemId, String itemName, LocalDate date) {
		this.itemId = itemId;
		this.itemName = itemName;
		this.date = date;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	


	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemName=" + itemName + ", date=" + date + "]";
	}
	
	
	
}
