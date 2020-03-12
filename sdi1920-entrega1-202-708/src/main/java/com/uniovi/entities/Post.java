package com.uniovi.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Post {
	@Id
	@GeneratedValue
	private Long id;
	private String titulo;
	private String description;
	private Date date;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public Post(Long id, String description, Date date) {
		super();
		this.id = id;
		this.description = description;
		this.date = date;
	}

	public Post(String description, Date date, User user) {
		super();
		this.description = description;
		this.date = date;
		this.user = user;
	}

	public Post() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
