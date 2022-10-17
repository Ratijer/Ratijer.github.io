package com.atijerarachel.checklists.entities;

import javax.persistence.*;

//Each item is in one shopping list
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

   //Index number determines the order in which the tasks will appear on the list
	private int indexNum;
	private boolean checkbox;
	private String itemName;
	private int quantity = 0;
	private double price = 0;
	
	public Item() {
		
	}
	
	public Item(boolean checkbox, String itemName, int quantity, double price)
	{
		this.checkbox = checkbox;
		this.itemName = itemName;
		this.quantity = quantity;
		this.price = price;
	}	
	
	public Item(String itemName, int quantity, double price)
	{
		this.itemName = itemName;
		this.quantity = quantity;
		this.price = price;
	}
	
	//Only itemName is required. If the user doesn't enter a value for quantity or price, the values for both will be zero.
	public Item(String itemName)
	{
		this.itemName = itemName;
		this.quantity = 0;
		this.price = 0;
	}
	
	//Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", indexNum=" + indexNum + ", checkbox=" + checkbox + ", itemName=" + itemName
				+ ", quantity=" + quantity + ", price=" + price + "]";
	}
}