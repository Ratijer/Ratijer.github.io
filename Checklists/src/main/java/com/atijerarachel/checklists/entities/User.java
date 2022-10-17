package com.atijerarachel.checklists.entities;

import java.util.Collection;

import javax.persistence.*;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = { "email", "accountName" }))
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String email;
	private String accountName;
	private String password;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Collection<Role> roles;

	// User lists
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "users_userlists", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "userLists_id", nullable = false, unique = true, referencedColumnName = "id"))
	private UserLists userLists;

	// Default constructor
	public User() {

	}

	public User(String email, String accountName, String password) {
		this.email = email;
		this.accountName = accountName;
		this.password = password;
	}
	
	public User(String email, String accountName, String password, Collection<Role> roles) {
		this.email = email;
		this.accountName = accountName;
		this.password = password;
		this.roles = roles;
	}

	public User(String email, String accountName, String password, UserLists userLists) {
		this.email = email;
		this.accountName = accountName;
		this.password = password;
		this.userLists = userLists;
	}

	// Getters and Setters
	public UserLists getUserLists() {
		return userLists;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserLists(UserLists userLists) {
		this.userLists = userLists;
	}

	   public Collection<Role> getRoles() {
	       return roles;
	   }

	   public void setRoles(Collection<Role> roles) {
	       this.roles = roles;
	   }
	   
	   @Override
	   public String toString() {
	       return "User{" +
	               "id=" + id +
	               ", accountName='" + accountName + '\'' +
	               ", email='" + email + '\'' +
	               ", password='" + "*********" + '\'' +
	               ", roles=" + roles +
	               '}';
	   }
}