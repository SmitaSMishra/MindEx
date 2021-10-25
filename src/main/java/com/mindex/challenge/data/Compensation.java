package com.mindex.challenge.data;

public class Compensation {
	private Emloyee employee;
	private float salary;
	private String effectiveData;
	
	public ReportingStructure() {
    }
	
	public Employee getEmployee() {
        return employee;
    }
	
	public Employee setEmployee(Employee employee) {
        this.employee = employee;
    }
	
	public float getSalary() {
        return salary;
    }
	
	public float setSalary(float salary) {
        this.salary = salary;
    }
	
	public String getEffectiveData() {
        return effectiveData;
    }
	
	public String setEffectiveData(String effectiveData) {
        this.effectiveData = effectiveData;
    }
	

}
