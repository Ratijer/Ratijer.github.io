package com.atijerarachel.checklists.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atijerarachel.checklists.dto.AddItemDto;
import com.atijerarachel.checklists.entities.Item;
import com.atijerarachel.checklists.entities.ShoppingList;
import com.atijerarachel.checklists.repository.ItemRepository;
import com.atijerarachel.checklists.repository.ShoppingListRepository;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private ItemRepository itemRepository;
	
	@Autowired
	private ShoppingListRepository shoppingListRepository;
	
	//Remove a item
	@Override
	public boolean remove(Item item) {	//Exception item null?
		boolean result = getUserShoppingList().getItems().remove(item);
		if(!result)
		{
			return false;
		}
		itemRepository.save(item);
		itemRepository.deleteById(item.getId());
		return true;
	}

	//Add item to DB
	@Override 
	public Item save(AddItemDto itemDto) {	//Exception no description
		Item item = new Item();
		//Get user's shopping list
		Set<Item> itemList = getUserShoppingList().getItems();
		
		//If shopping list is empty, reset shopping list nextIndexNum to 0
		if(itemList.isEmpty())
		{
			getUserShoppingList().setNextIndexNum(0);
		}

		//Set next index
		item.setIndexNum(getUserShoppingList().getNextIndexNum() + 1);
		getUserShoppingList().setNextIndexNum(getUserShoppingList().getNextIndexNum() + 1);
		
		item.setCheckbox(false);
		item.setItemName(itemDto.getItemName());
		item.setQuantity(itemDto.getQuantity());
		item.setPrice(itemDto.getPrice());
		//Add item to Shoppinglist and shopping_items table in the DB
		itemList.add(item);
		
		return itemRepository.save(item);
	}
	
	//Get current user's shopping list
	@Override
	public ShoppingList getUserShoppingList() {
		return userService.getCurrentUser().getUserLists().getShoppingList();
	}
	
	//Get a collection of items from current user's shopping list
	@Override
	public Collection<Item> getItemsByCurrentUserShoppingList(){	
		return itemRepository.getItemsByShoppingList(getUserShoppingList().getId());
	}
	
	@Override
	public Collection<Item> getItemsByShoppingListId(Long shoppingId){	
		return itemRepository.getItemsByShoppingList(shoppingId);
	}
	
	//Change the counts for purchased and unpurchased items depending on the status of the checkbox
	@Override 
	public void checkboxCount(Item item, boolean remove)
	{
		ShoppingList userShoppingList  = getUserShoppingList();
		
		//Subtract counts when a item is being removed
		if(remove == true)
		{
			//Count checked/unchecked boxes
			if (item.isCheckbox() == true)
			{
				userShoppingList.setNumOfPurchasedItems(userShoppingList.getNumOfPurchasedItems() - 1);
			}
			else if (item.isCheckbox() == false)
			{
				userShoppingList.setNumOfUnpurchasedItems(userShoppingList.getNumOfUnpurchasedItems() - 1);
			}
		}	
		//Edit counts when a user clicks on the checkbox
		else
		{
			if (item.isCheckbox() == true)
			{
				userShoppingList.setNumOfPurchasedItems(userShoppingList.getNumOfPurchasedItems() + 1);
				userShoppingList.setNumOfUnpurchasedItems(userShoppingList.getNumOfUnpurchasedItems() - 1);
			}
			else if (item.isCheckbox() == false)
			{
				userShoppingList.setNumOfPurchasedItems(userShoppingList.getNumOfPurchasedItems() - 1);
				userShoppingList.setNumOfUnpurchasedItems(userShoppingList.getNumOfUnpurchasedItems() + 1);
			}
		}
		
		shoppingListRepository.save(userShoppingList);
	}
}
