// package com.example.demo;

// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;

// @Entity
// public class Employee {
	
// 	@Id
// 	private String id;
	
// 	private String name;
	
// 	private double salary;
	

// 	@ManyToOne
// 	@JoinColumn(name = "department_id")
// 	private Department department;
	

// 	public String getName() {
// 		return name;
// 	}




// 	public void setName(String name) {
// 		this.name = name;
// 	}




// 	public double getSalary() {
// 		return salary;
// 	}




// 	public void setSalary(double salary) {
// 		this.salary = salary;
// 	}




// 	public Employee(String name, double salary) {
		
// 		this.name = name;
// 		this.salary = salary;
// 	}




// 	public String getId() {
// 		return id;
// 	}




// 	public void setId(String id) {
// 		this.id = id;
// 	}




// 	public Department getDepartment() {
// 		return department;
// 	}




// 	public void setDepartment(Department department) {
// 		this.department = department;
// 	}




// 	@Override
// 	public String toString() {
// 		return "Employee [name=" + name + ", salary=" + salary + "]";
// 	}
	
	
	

// }
