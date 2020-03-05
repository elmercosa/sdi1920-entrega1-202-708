package com.uniovi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_friends")
public class Friends {
	
	@Id
	@GeneratedValue
	private long id;

	@Column(name = "personId")
	Long personId;

	@Column(name = "friendId")
	Long friendId;

}
