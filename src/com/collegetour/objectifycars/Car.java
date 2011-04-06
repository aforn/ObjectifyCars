package com.collegetour.objectifycars;

import javax.persistence.*;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Car {
	@Id public Long id;
	private String license;
	
	@Parent public Key<Person> owner;
	
	private Car() {
		//nothing
	} //zero argument constructor
	
	public Car(Key<Person> owner) {
		this();
		this.owner = owner;
	} //1 arg const
	
	public void setId(Long newId) {
		id = newId;
	} //setId
	
	public Long getId() {
		return id;
	} //getId
	
	public void setLicense(String newLicense) {
		license = newLicense;
	} //setLicense
	
	public String getLicense() {
		return license;
	} //getlicense
	
	public void setKey(Long newId) {
		id = newId;
	} //setId
	
	public Key<Person> getOwner() {
		return owner;
	} //getOwner
	
	public void setOwner(Key<Person> newOwner) {
		owner = newOwner;
	} //setOwner

}
