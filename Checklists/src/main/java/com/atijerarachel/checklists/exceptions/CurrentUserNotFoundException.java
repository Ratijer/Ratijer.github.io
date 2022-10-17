package com.atijerarachel.checklists.exceptions;

//The currently signed in user cannot be found
@SuppressWarnings("serial")
public class CurrentUserNotFoundException extends Exception  {
	public CurrentUserNotFoundException(String error)
	{
		super(error);  
	}
}
