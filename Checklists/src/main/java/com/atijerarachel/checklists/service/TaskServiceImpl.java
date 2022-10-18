package com.atijerarachel.checklists.service;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atijerarachel.checklists.dto.AddTaskDto;
import com.atijerarachel.checklists.entities.Task;
import com.atijerarachel.checklists.entities.TodoList;
import com.atijerarachel.checklists.repository.TaskRepository;
import com.atijerarachel.checklists.repository.TodoListRepository;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private TaskRepository taskRepository;
	
	@Autowired
	private TodoListRepository todoListRepository;
	
	//Remove a task
	@Override
	public boolean remove(Task task) {	//Exception task null?
		boolean result = getUserTodoList().getTasks().remove(task);
		if(!result)
		{
			return false;
		}
		taskRepository.save(task);
		taskRepository.deleteById(task.getId());
		return true;
	}

	//Add task to DB
	@Override 
	public Task save(AddTaskDto taskDto) {	//Exception no description
		Task task = new Task();
		//Get user's to-do list
		Set<Task> taskList = getUserTodoList().getTasks();
		
		//If to-do list is empty, reset to-do list nextIndexNum to 0
		if(taskList.isEmpty())
		{
			getUserTodoList().setNextIndexNum(0);
		}

		//Set next index
		task.setIndexNum(getUserTodoList().getNextIndexNum() + 1);
		getUserTodoList().setNextIndexNum(getUserTodoList().getNextIndexNum() + 1);
		
		task.setCheckbox(false);
		task.setTaskDesc(taskDto.getTaskDesc());
		//Add task to Todolist and todo_tasks table in the DB
		taskList.add(task);
		
		return taskRepository.save(task);
	}
	
	//Get current user's to-do list
	@Override
	public TodoList getUserTodoList() {
		return userService.getCurrentUser().getUserLists().getTodoList();
	}
	
	//Get a collection of tasks from current user's to-do list
	@Override
	public Collection<Task> getTasksByCurrentUserTodoList(){	
		return taskRepository.getTasksByTodoList(getUserTodoList().getId());
	}
	
	@Override
	public Collection<Task> getTasksByTodoListId(Long todoId){	
		return taskRepository.getTasksByTodoList(todoId);
	}
	
	//Change the counts for completed and uncompleted tasks depending on the status of the checkbox
	@Override 
	public void checkboxCount(Task task, boolean remove)
	{
		TodoList userTodoList  = getUserTodoList();
		
		//Subtract counts when a task is being removed
		if(remove == true)
		{
			//Count checked/unchecked boxes
			if (task.isCheckbox() == true)
			{
				userTodoList.setNumOfCompletedTasks(userTodoList.getNumOfCompletedTasks() - 1);
			}
			else if (task.isCheckbox() == false)
			{
				userTodoList.setNumOfUncompletedTasks(userTodoList.getNumOfUncompletedTasks() - 1);
			}
		}	
		//Edit counts when a user clicks on the checkbox
		else
		{
			if (task.isCheckbox() == true)
			{
				userTodoList.setNumOfCompletedTasks(userTodoList.getNumOfCompletedTasks() + 1);
				userTodoList.setNumOfUncompletedTasks(userTodoList.getNumOfUncompletedTasks() - 1);
			}
			else if (task.isCheckbox() == false)
			{
				userTodoList.setNumOfCompletedTasks(userTodoList.getNumOfCompletedTasks() - 1);
				userTodoList.setNumOfUncompletedTasks(userTodoList.getNumOfUncompletedTasks() + 1);
			}
		}
		
		todoListRepository.save(userTodoList);
	}
}
