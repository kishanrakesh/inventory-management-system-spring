package com.maybank.ims.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.maybank.ims.DAO.ItemDAO;
import com.maybank.ims.model.Item;

@RestController
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	ItemDAO itemDAO;

	@Override
	public boolean addItem(Item item) {
		itemDAO.save(item);
		return true;
	}

	@Override
	public boolean updateItem(Item item) {
		itemDAO.save(item);
		return true;
	}

	@Override
	public boolean deleteItem(int itemId) {
		itemDAO.deleteById(itemId);
		return true;
	}

	@Override
	public List<Item> viewItems() {
		return (List<Item>)itemDAO.findAll();
	}

	@Override
	public Item searchItemById(int itemId) {
		return itemDAO.findById(itemId).get();
	}

	@Override
	public List<Item> searchItemByName(String itemName) {
		return itemDAO.findByItemName(itemName);
	}

	@Override
	public boolean isItemExists(int itemId) {
		return itemDAO.existsById(itemId);
	}

	@Override
	public List<Item> viewUnassignedItems() {
		return itemDAO.findByDate(null);
	}
	
}
