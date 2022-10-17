package com.atijerarachel.checklists.entities;

import javax.persistence.*;

//Each task is in one To-do list
@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	//Index number determines the order in which the tasks will appear on the list
	private int indexNum;
	private boolean checkbox;
	private String taskDesc;
	
	public Task() {
		
	}
	
	public Task(String taskDesc)
	{
		this.taskDesc = taskDesc;
	}
	
	public Task(boolean checkbox, String taskDesc)
	{
		this.checkbox = checkbox;
		this.taskDesc = taskDesc;
	}
	
	//Getters and Setters
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(int indexNum) {
		this.indexNum = indexNum;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", indexNum=" + indexNum + ", checkbox=" + checkbox + ", taskDesc=" + taskDesc + "]";
	}
}