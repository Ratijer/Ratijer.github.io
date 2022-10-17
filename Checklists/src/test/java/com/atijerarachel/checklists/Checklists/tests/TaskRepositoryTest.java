package com.atijerarachel.checklists.Checklists.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.atijerarachel.checklists.entities.Role;
import com.atijerarachel.checklists.entities.ShoppingList;
import com.atijerarachel.checklists.entities.Task;
import com.atijerarachel.checklists.entities.TodoList;
import com.atijerarachel.checklists.entities.User;
import com.atijerarachel.checklists.entities.UserLists;
import com.atijerarachel.checklists.repository.TaskRepository;
import com.atijerarachel.checklists.repository.UserRepository;
import com.atijerarachel.checklists.service.TaskService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	TaskRepository taskRepository;
	
	@Autowired
	TaskService taskService;

	// Initialize users
	User validUser1 = new User("OnetestEmail@email.com", "validUser1", "12345678");

	// Initialize tasks
	Task task1 = new Task("The first task");	//Should be the first task in the list
	Task task2 = new Task("The second task");	//Should be second in list
	Task task3 = new Task("The third task");	//Should be third in list
	
	Task[] validTaskArray = {task1, task2, task3};
	
	//Initialize Task list and To-do list
	Set<Task> taskList;
	TodoList tl = new TodoList();

	// Create users and put them in database before doing tests
	@BeforeAll
	void setUpBeforeClass() {
		// Input users into DB.
		// User list related
		ShoppingList sl = new ShoppingList();
		UserLists ui = new UserLists(tl, sl);

		validUser1.setUserLists(ui);
		validUser1.setRoles(Arrays.asList(new Role("ROLE_USER")));
		userRepository.save(validUser1);
		
		//Initialize to-do list
		Set<Task> taskIni = new HashSet<>();
		validUser1.getUserLists().getTodoList().ListTasks(taskIni);
		//Set to-do list and taskList
		TodoList tl = validUser1.getUserLists().getTodoList();
		taskList = tl.getTasks();

		// Input tasks in DB
		for(Task newTask : validTaskArray)
		{
			Task task = new Task();

			// If to-do list is empty, first index is 1
			if (taskList.isEmpty()) {
				task.setIndexNum(1);
			} else {
				// Set next index
				task.setIndexNum(taskList.size() + 1);
			}
			task.setCheckbox(false);
			task.setTaskDesc(newTask.getTaskDesc());
			// Add task to Todolist and todo_tasks table in the DB
			taskList.add(task);
			newTask = taskRepository.save(task);
		}
	}
	
	//Make sure queries are not null
	@Test
	void queriesNotNullTest() {
		//Get all tasks from a specified to-do list
		assertNotNull(taskRepository.getTasksByTodoList(tl.getId()));
		//Get task by indexNum
		assertNotNull(taskRepository.getTaskByIndexNum(tl.getId(), 2));
	}

	// Remove all test users
	@AfterAll
	void tearDownAfterClass() {
		userRepository.delete(validUser1);
//		validUser1.getUserLists().getTodoList().getTasks().clear();
	}
	
	void taskListPrint(Set<Task> taskList) {
		for(Task task : taskList)
		{
			System.out.println(task.getId());
		}
		System.out.println();
	}
}
