package com.atijerarachel.checklists.service;

import java.util.Collection;

import com.atijerarachel.checklists.dto.AddTaskDto;
import com.atijerarachel.checklists.entities.Task;
import com.atijerarachel.checklists.entities.TodoList;

public interface TaskService {
	//Input a new task in the DB
	Task save(AddTaskDto taskDto);

	// Get the current user's to-do list
	TodoList getUserTodoList();

	// Remove a task
	boolean remove(Task task);

	// Get a collection of tasks from current user's to-do list
	Collection<Task> getTasksByCurrentUserTodoList();

	// Get tasks through todo list id
	Collection<Task> getTasksByTodoListId(Long todoId);

	// Change the counts for completed and uncompleted tasks depending on the status
	// of the checkbox
	void checkboxCount(Task task, boolean remove);

}
