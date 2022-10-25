package com.atijerarachel.checklists.dto;

import javax.validation.constraints.NotEmpty;

public class AddItemDto {
	
	@NotEmpty
	private String itemName;
	private int quantity = 1;
	private double price = 0;
	
	public AddItemDto() {
		
	}

	public AddItemDto(@NotEmpty String itemName) {
		this.itemName = itemName;
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
}
