package com.learnings.HibernateInitials.Entity;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.JoinTable;
//import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;

@Entity
public class Laptop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id;
	
	private String model;
	
	@ManyToMany(mappedBy = "laptop", cascade = CascadeType.ALL)
	private List<Student> student = new ArrayList<>();


	public List<Student> getStudent() {
		return student;
	}

	public void setStudent(List<Student> student) {
		this.student = student;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	public List<Student> getLaptop() {
		return student;
	}

	public void setLaptop(List<Student> student) {
		this.student = student;
	}
}
