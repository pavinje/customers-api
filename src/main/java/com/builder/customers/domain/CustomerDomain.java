package com.builder.customers.domain;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "customer")
public class CustomerDomain {
	
	@Id
	private String id;
	private String name;
	private String document;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar birthdate;
	
	@Transient
	private Integer age;
	
	

	public Integer getAge() {
		if (birthdate != null) {
			LocalDate today = LocalDate.now();
			LocalDate birth = LocalDate.ofInstant(birthdate.toInstant(), ZoneId.systemDefault());
			
			return Period.between(birth, today).getYears();
		}
		return null;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public CustomerDomain(String id) {
		this.id = id;
	}

	public CustomerDomain() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public Calendar getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Calendar birthdate) {
		this.birthdate = birthdate;
	}
	
	

}
