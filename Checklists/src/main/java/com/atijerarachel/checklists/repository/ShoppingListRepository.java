package com.atijerarachel.checklists.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.atijerarachel.checklists.entities.ShoppingList;

public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long>{

}
