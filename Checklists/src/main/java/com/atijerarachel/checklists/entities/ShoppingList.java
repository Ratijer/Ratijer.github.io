package com.atijerarachel.checklists.entities;

import java.util.Set;

import javax.persistence.*;

//Each shopping list has many items
@Entity
public class ShoppingList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	//Items
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "shopping_items", joinColumns = @JoinColumn(name = "shopping_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "item_id", referencedColumnName = "id"))
	private Set<Item> items;
	
	// Statistic Counts
	private int totalNumberOfItems = 0;	//Determined by quantity
	private int numOfCompletedItems = 0;
	private int numOfUncompletedItems = 0;
	private double totalPriceOfItems = 0;
	
	//Constructors
	public ShoppingList()
	{
		
	}
	public ShoppingList(Set<Item> items) {
		this.items = items;
	}
	
	//Getters and Setters
	public int getTotalNumberOfItems() {
		return totalNumberOfItems;
	}
	public void setTotalNumberOfItems(int totalNumberOfItems) {
		this.totalNumberOfItems = totalNumberOfItems;
	}
	public int getNumOfCompletedItems() {
		return numOfCompletedItems;
	}
	public void setNumOfCompletedItems(int numOfCompletedItems) {
		this.numOfCompletedItems = numOfCompletedItems;
	}
	public int getNumOfUncompletedItems() {
		return numOfUncompletedItems;
	}
	public void setNumOfUncompletedItems(int numOfUncompletedItems) {
		this.numOfUncompletedItems = numOfUncompletedItems;
	}
	public double getTotalPriceOfItems() {
		return totalPriceOfItems;
	}
	public void setTotalPriceOfItems(double totalPriceOfItems) {
		this.totalPriceOfItems = totalPriceOfItems;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "ShoppingList [id=" + id + ", items=" + items + ", totalNumberOfItems=" + totalNumberOfItems
				+ ", numOfCompletedItems=" + numOfCompletedItems + ", numOfUncompletedItems=" + numOfUncompletedItems
				+ ", totalPriceOfItems=" + totalPriceOfItems + "]";
	}
}