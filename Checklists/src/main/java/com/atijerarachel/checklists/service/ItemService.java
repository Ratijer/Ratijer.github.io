package com.atijerarachel.checklists.service;

import java.util.Collection;

import com.atijerarachel.checklists.dto.AddItemDto;
import com.atijerarachel.checklists.entities.Item;
import com.atijerarachel.checklists.entities.ShoppingList;

public interface ItemService {
	//Input a new item in the DB
	Item save(AddItemDto itemDto);

	// Get the current user's shopping list
	ShoppingList getUserShoppingList();

	// Remove a item
	boolean remove(Item item);

	// Get a collection of items from current user's shopping list
	Collection<Item> getItemsByCurrentUserShoppingList();

	// Get items through shopping list id
	Collection<Item> getItemsByShoppingListId(Long shoppingId);

	// Change the counts for completed and uncompleted items depending on the status
	// of the checkbox
	void checkboxCount(Item item, boolean remove);
}
