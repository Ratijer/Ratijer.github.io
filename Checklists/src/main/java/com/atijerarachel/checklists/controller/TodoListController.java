package com.atijerarachel.checklists.controller;

import java.util.Collection;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atijerarachel.checklists.dto.AddTaskDto;
import com.atijerarachel.checklists.entities.Task;
import com.atijerarachel.checklists.entities.TodoList;
import com.atijerarachel.checklists.repository.TaskRepository;
import com.atijerarachel.checklists.repository.TodoListRepository;
import com.atijerarachel.checklists.service.TaskService;

//Insert new tasks to the DB
@Controller
@RequestMapping("/todouser")
public class TodoListController {
	Logger logger = LoggerFactory.getLogger(TodoListController.class);

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	TodoListRepository todoListRepository;
	

	@ModelAttribute("task")
	public AddTaskDto addTaskDto() {
		return new AddTaskDto();
	}

	@GetMapping("/list-page")
	public String showTodouserForm(Model model) {
		// Get list of tasks from the user's to-do list
		Collection<Task> taskList = taskService.getTasksByCurrentUserTodoList();
		model.addAttribute("taskList", taskList);

		// Get to-do list
		// Counters
		TodoList todoList = taskService.getUserTodoList();
		model.addAttribute("total", todoList.getTotalNumberofTasks()); // Total number of tasks
		model.addAttribute("completed", todoList.getNumOfCompletedTasks()); // Completed tasks
		model.addAttribute("uncompleted", todoList.getNumOfUncompletedTasks()); // Uncompleted tasks

		return "todouser";
	}

	@PostMapping("/add") // Uses th:href
	public String addTask(@ModelAttribute("task") @Valid AddTaskDto taskDto, BindingResult result, Model model) {
		TodoList userTodoList = taskService.getUserTodoList();
		
		if (result.hasErrors()) {
			return "todouser";
		}

		taskService.save(taskDto);
		// Do counts. Increment the total number of tasks by one in the counter.
		userTodoList.setTotalNumberofTasks(userTodoList.getTotalNumberofTasks() + 1);
		userTodoList.setNumOfUncompletedTasks(userTodoList.getNumOfUncompletedTasks() + 1);
		todoListRepository.save(userTodoList);
		logger.info("Task successfully added");

		return "redirect:/todouser/list-page";
	}

	@PostMapping("/remove") // Uses th:action
	public String removeTask(@RequestParam Long taskId) {
		TodoList userTodoList = taskService.getUserTodoList();
		Task taskToRemove = taskRepository.findById(taskId).get();
		
		//Reduce total number
		userTodoList.setTotalNumberofTasks(userTodoList.getTotalNumberofTasks() - 1);
		//Checkbox counts
		taskService.checkboxCount(taskToRemove, true);
		
		//Remove task
		taskService.remove(taskToRemove);
		logger.info("Task successfully removed");
		
		return "redirect:/todouser/list-page";
	}

	// Check or uncheck checkbox
	@PostMapping("/update") // Uses th:action
	public String updateTask(@RequestParam Long taskId) {
		Task task = taskRepository.findById(taskId).get();

		task.setCheckbox(!task.isCheckbox());
		taskRepository.save(task);
		
		//Count checked/unchecked boxes
		//Change the counts for completed and uncompleted tasks depending on the status of the checkbox
		taskService.checkboxCount(task, false);
		logger.info("Task successfully updated");

		return "redirect:/todouser/list-page";
	}
}
