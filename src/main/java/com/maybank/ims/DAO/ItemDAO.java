package com.maybank.ims.DAO;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.maybank.ims.model.Item;

public interface ItemDAO extends CrudRepository<Item, Integer>{
	public List<Item> findByItemName(String itemName);
	public List<Item> findByDate(LocalDate date);
}
