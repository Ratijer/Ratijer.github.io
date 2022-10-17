package com.atijerarachel.checklists.dto;

import javax.validation.constraints.NotEmpty;

public class AddTaskDto {
	
	@NotEmpty
	private String taskDesc;
	
	public AddTaskDto() {
		
	}

	public AddTaskDto(@NotEmpty String taskDesc) {
		this.taskDesc = taskDesc;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
}
