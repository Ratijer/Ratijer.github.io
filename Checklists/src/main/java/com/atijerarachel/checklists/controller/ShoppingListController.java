package com.atijerarachel.checklists.controller;

import java.util.Collection;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atijerarachel.checklists.dto.AddItemDto;
import com.atijerarachel.checklists.entities.Item;
import com.atijerarachel.checklists.entities.ShoppingList;
import com.atijerarachel.checklists.repository.ItemRepository;
import com.atijerarachel.checklists.repository.ShoppingListRepository;
import com.atijerarachel.checklists.service.ItemService;

//Insert new items to the DB
@Controller
@RequestMapping("/shoppinguser")
public class ShoppingListController {
	Logger logger = LoggerFactory.getLogger(ShoppingListController.class);

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	ShoppingListRepository shoppingListRepository;
	

	@ModelAttribute("item")
	public AddItemDto addItemDto() {
		return new AddItemDto();
	}

	@GetMapping("/list-page")
	public String showShoppingUserForm(Model model) {
		// Get list of items from the user's shopping list
		Collection<Item> itemList = itemService.getItemsByCurrentUserShoppingList();
		model.addAttribute("itemList", itemList);

		// Get shopping list
		// Counters
		ShoppingList shoppingList = itemService.getUserShoppingList();
		model.addAttribute("total", shoppingList.getTotalNumberOfItems()); // Total number of items
		model.addAttribute("purchased", shoppingList.getNumOfPurchasedItems()); // Completed items
		model.addAttribute("unpurchased", shoppingList.getNumOfUnpurchasedItems()); // Uncompleted items

		return "shoppinguser";
	}

	@PostMapping("/add") // Uses th:href
	public String addItem(@ModelAttribute("item") @Valid AddItemDto itemDto, BindingResult result, Model model) {
		ShoppingList userShoppingList = itemService.getUserShoppingList();
		
		if (result.hasErrors()) {
			return "shoppinguser";
		}

		itemService.save(itemDto);
		// Do counts. Increment the total number of items by one in the counter.
		userShoppingList.setTotalNumberOfItems(userShoppingList.getTotalNumberOfItems() + 1);
		userShoppingList.setNumOfUnpurchasedItems(userShoppingList.getNumOfUnpurchasedItems() + 1);
		shoppingListRepository.save(userShoppingList);
		logger.info("Item successfully added");

		return "redirect:/shoppinguser/list-page";
	}

	@PostMapping("/remove") // Uses th:action
	public String removeItem(@RequestParam Long itemId) {
		ShoppingList userShoppingList = itemService.getUserShoppingList();
		Item itemToRemove = itemRepository.findById(itemId).get();
		
		//Reduce total number
		userShoppingList.setTotalNumberOfItems(userShoppingList.getTotalNumberOfItems() - 1);
		//Checkbox counts
		itemService.checkboxCount(itemToRemove, true);
		
		//Remove item
		itemService.remove(itemToRemove);
		logger.info("Item successfully removed");
		
		return "redirect:/shoppinguser/list-page";
	}

	// Check or uncheck checkbox
	@PostMapping("/update") // Uses th:action
	public String updateItem(@RequestParam Long itemId) {
		Item item = itemRepository.findById(itemId).get();

		item.setCheckbox(!item.isCheckbox());
		itemRepository.save(item);
		
		//Count checked/unchecked boxes
		//Change the counts for purchased and unpurchased items depending on the status of the checkbox
		itemService.checkboxCount(item, false);
		logger.info("Item successfully updated");

		return "redirect:/shoppinguser/list-page";
	}
}
