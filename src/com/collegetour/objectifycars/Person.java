package com.collegetour.objectifycars;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class Person {
	@Id private Long id;
	private String name;
	
	public void Person(Long id, String name) {
		this.id = id;
		this.name = name;
	} //2 param constructor
	
	public void Person(String name) {
		this.name = name;
	} //1 arg constructor
	
	public void Person() {
		//nothing
	} //0 arg constructor
	
	public Long getId() {
		return id;
	} //getId
	
	public void setId(Long newId) {
		id = newId;
	} //setId
	
	public String getName() {
		return name;
	} //getName
	
	public void setId(String newName) {
		name = newName;
	} //setName
	

}
