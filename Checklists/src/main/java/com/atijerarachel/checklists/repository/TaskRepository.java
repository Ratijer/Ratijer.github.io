package com.atijerarachel.checklists.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.atijerarachel.checklists.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	//Get all tasks from a specified to-do list
	@Query(value = "SELECT * FROM task LEFT JOIN todo_tasks ON "
			+ "task.id = todo_tasks.task_id WHERE todo_tasks.todo_id = ?1 "
			+ "ORDER BY task.index_num", nativeQuery = true)
	Collection<Task> getTasksByTodoList(Long todoId);
	
	//Get task by indexNum
	@Query(value = "SELECT * FROM task LEFT JOIN todo_tasks ON "
			+ "task.id = todo_tasks.task_id WHERE todo_tasks.todo_id = ?1 "
			+ "AND task.index_num = ?2", nativeQuery = true)
	Collection<Task> getTaskByIndexNum(Long todoId, int taskIndex);
}
