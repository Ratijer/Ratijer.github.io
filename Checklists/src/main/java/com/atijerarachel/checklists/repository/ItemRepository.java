package com.atijerarachel.checklists.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.atijerarachel.checklists.entities.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
	//Get all items from a specified shopping list
	@Query(value = "SELECT * FROM item LEFT JOIN shopping_items ON "
			+ "item.id = shopping_items.item_id WHERE shopping_items.shopping_id = ?1 "
			+ "ORDER BY item.index_num", nativeQuery = true)
	Collection<Item> getItemsByShoppingList(Long shoppingId);
	
	//Get item by indexNum
	@Query(value = "SELECT * FROM item LEFT JOIN shopping_items ON "
			+ "item.id = shopping_items.item_id WHERE shopping_items.shopping_id = ?1 "
			+ "AND item.index_num = ?2", nativeQuery = true)
	Collection<Item> getItemByIndexNum(Long shoppingId, int itemIndex);
}
