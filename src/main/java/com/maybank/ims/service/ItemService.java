package com.maybank.ims.service;

import java.util.List;

import com.maybank.ims.model.Item;

public interface ItemService {
	public boolean addItem(Item item);
	public boolean updateItem(Item item);
	public boolean deleteItem(int itemId);
	public List<Item> viewItems();

	public List<Item> viewUnassignedItems();
	public Item searchItemById(int itemId);
	public List<Item> searchItemByName(String itemName);
	public boolean isItemExists(int itemId);
	
}
