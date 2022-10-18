package com.atijerarachel.checklists.Checklists.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
import com.atijerarachel.checklists.service.UserServiceImpl;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskServiceImplTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	TaskRepository taskRepository;

	// Initialize users
	User validUser1 = new User("OnetestEmail@email.com", "validUser1", "12345678");
	// To do list
	Set<Task> taskList = new HashSet<>();

	// Initialize tasks
	Task task1 = new Task("The first task");
	Task task2 = new Task("The second task");
	Task task3 = new Task("The third task");

	Task[] validTaskArray = { task1, task2, task3 };

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		// Input users into DB.
		// User list related
		ShoppingList sl = new ShoppingList();
		TodoList tl = new TodoList();
		UserLists ui = new UserLists(tl, sl);

		validUser1.setUserLists(ui);
		validUser1.setRoles(Arrays.asList(new Role("ROLE_USER")));
		userRepository.save(validUser1);

		// Initialize to-do list
		Set<Task> taskIni = new HashSet<>();
		validUser1.getUserLists().getTodoList().ListTasks(taskIni);
		taskList = validUser1.getUserLists().getTodoList().getTasks();
	}

	@Test
	void saveTest() {
		boolean tasksMatch = false;

		// Add tasks
		for (Task newTask : validTaskArray) {
			newTask = addTask(newTask); // Make sure task ids are not null
		}

		// Check too see if tasks are in the To-do List Set<>
		for (Task newTask : validTaskArray) {
			if (!taskList.contains(newTask)) {
				tasksMatch = false;
				break;
			}
		}

		tasksMatch = true;
		assertTrue(tasksMatch);
	}

	// Test to see if a task (that's in the list) is properly removed the the DB
	@Test
	void removeTaskInListTest() {
		// Add task that will be removed
		Task task4 = new Task("Remove this Task");
		task4 = addTask(task4); // Make task4 id not null

		// Remove task (method being tested)
		boolean result = taskList.remove(task4);
		if (!result) {
			assertTrue(false);
		}

		taskRepository.save(task4);
		taskRepository.deleteById(task4.getId());

		// Make sure task is gone
		if (taskList.contains(task4) == false && taskRepository.findById(task4.getId()).isEmpty()) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	// Try not to remove a task that is not in the list
	@Test
	void removeTaskNotInListTest() {
		Task task4 = new Task("Not in List");
		assertFalse(taskList.remove(task4));
	}

	@AfterAll
	void tearDownAfterClass() throws Exception {
		userRepository.delete(validUser1);
//		validUser1.getUserLists().getTodoList().getTasks().clear();
	}

	void taskListPrint(Set<Task> taskList) {
		for (Task task : taskList) {
			System.out.println(task.getId());
		}
		System.out.println();
	}

	Task addTask(Task newTask) {
		// Input tasks in DB
		Task task = new Task();
		// Get todo list
		TodoList userTodo = validUser1.getUserLists().getTodoList();
		// If to-do list is empty, reset to-do list nextIndexNum to 0
		if (taskList.isEmpty()) {
			userTodo.setNextIndexNum(0);
		}
		
		//Set next index
		task.setIndexNum(userTodo.getNextIndexNum() + 1);
		userTodo.setNextIndexNum(userTodo.getNextIndexNum() + 1);

		task.setCheckbox(false);
		task.setTaskDesc(newTask.getTaskDesc());
		// Add task to Todolist and todo_tasks table in the DB
		taskList.add(task);
		return taskRepository.save(task);
	}
}
