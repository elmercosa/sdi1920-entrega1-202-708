package com.uniovi.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue
	private long id;
	private String name;
	private String lastName;
	@Column(unique = true)
	private String email;

	private String password;
	@Transient // propiedad que no se almacena e la tabla.
	private String passwordConfirm;
	
	@Transient
	private boolean friendRequest;

	private String role;

	@ManyToMany
	@JoinTable(name = "tbl_friends", joinColumns = @JoinColumn(name = "personId"), inverseJoinColumns = @JoinColumn(name = "friendId"))
	private Set<User> friends;

	@ManyToMany
	@JoinTable(name = "tbl_friends", joinColumns = @JoinColumn(name = "friendId"), inverseJoinColumns = @JoinColumn(name = "personId"))
	private Set<User> friendOf;

	@ManyToMany
	@JoinTable(name = "tbl_friendsrequest", joinColumns = @JoinColumn(name = "personId"), inverseJoinColumns = @JoinColumn(name = "friendId"))
	private Set<User> friendrequest;

	@ManyToMany
	@JoinTable(name = "tbl_friendsrequest", joinColumns = @JoinColumn(name = "friendId"), inverseJoinColumns = @JoinColumn(name = "personId"))
	private Set<User> friendrequestOf;

	public User() {
	}

	public User(String name, String lastName, String email, String password) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<User> getFriends() {
		return friends;
	}

	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}

	public Set<User> getFriendrequest() {
		return friendrequest;
	}

	public void setFriendrequest(Set<User> friendrequest) {
		this.friendrequest = friendrequest;
	}

	public String getFullName() {
		return this.name + " " + this.lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	public boolean isFriendRequest() {
		return friendRequest;
	}

	public void setFriendRequest(boolean friendRequest) {
		this.friendRequest = friendRequest;
	}

	@Override
	public String toString() {
		return "User [friendrequest=" + friendrequest + ", friendrequestOf=" + friendrequestOf + "]";
	}
	
	

}
