package com.atijerarachel.checklists.entities;

import java.util.Set;

import javax.persistence.*;

//Each to-do list has many tasks
@Entity
public class TodoList {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	// Tasks
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "todo_tasks", joinColumns = @JoinColumn(name = "todo_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
	private Set<Task> tasks;

	// Statistic Counts
	private int totalNumberOfTasks = 0;
	private int numOfCompletedTasks = 0;
	private int numOfUncompletedTasks = 0;
	
	//Next index_num of a new task
	private int nextIndexNum = 0;

	// Constructors
	public TodoList() {

	}

	public TodoList(Set<Task> tasks) {
		this.tasks = tasks;
	}

	// Getters and Setters
	public int getTotalNumberofTasks() {
		return totalNumberOfTasks;
	}

	public void setTotalNumberofTasks(int totalNumberofTasks) {
		this.totalNumberOfTasks = totalNumberofTasks;
	}

	public int getNumOfCompletedTasks() {
		return numOfCompletedTasks;
	}

	public void setNumOfCompletedTasks(int numOfCompletedTasks) {
		this.numOfCompletedTasks = numOfCompletedTasks;
	}

	public int getNumOfUncompletedTasks() {
		return numOfUncompletedTasks;
	}

	public void setNumOfUncompletedTasks(int numOfUncompletedTasks) {
		this.numOfUncompletedTasks = numOfUncompletedTasks;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void ListTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	public Long getId() {
		return id;
	}

	public void ListId(Long id) {
		this.id = id;
	}
	
	public int getNextIndexNum() {
		return nextIndexNum;
	}

	public void setNextIndexNum(int nextIndexNum) {
		this.nextIndexNum = nextIndexNum;
	}

	@Override
	public String toString() {
		return "TodoList [id=" + id + ", tasks=" + tasks + ", totalNumberOfTasks=" + totalNumberOfTasks
				+ ", numOfCompletedTasks=" + numOfCompletedTasks + ", numOfUncompletedTasks=" + numOfUncompletedTasks
				+ "]";
	}
}