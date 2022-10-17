package com.atijerarachel.checklists.entities;

import javax.persistence.*;

//Has a set of lists (one Todo list and one Shopping list) that belongs to the user
@Entity
public class UserLists {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	//Todo list
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "userLists_todo", joinColumns = @JoinColumn(name = "userLists_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "todo_id", unique = true, referencedColumnName = "id"))
	private TodoList todoList;
	
	//Shopping list
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "userLists_shopping", joinColumns = @JoinColumn(name = "userLists_id", referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "shopping_id", unique = true, referencedColumnName = "id"))
	private ShoppingList shoppingList;

	//Constructors
	public UserLists()
	{
		
	}
	public UserLists(TodoList todoList, ShoppingList shoppingList) {
		this.todoList = todoList;
		this.shoppingList = shoppingList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TodoList getTodoList() {
		return todoList;
	}

	public void setTodoList(TodoList todoList) {
		this.todoList = todoList;
	}

	public ShoppingList getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(ShoppingList shoppingList) {
		this.shoppingList = shoppingList;
	}
}