package com.atijerarachel.checklists.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.atijerarachel.checklists.entities.TodoList;

public interface TodoListRepository extends JpaRepository<TodoList, Long>{
	//Get total number of To-do Lists
	  @Query(value = "SELECT COUNT(todo_list.id) FROM todo_list", nativeQuery = true)
	  int getTotalTodoLists();
}
