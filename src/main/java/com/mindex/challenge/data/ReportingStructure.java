package com.mindex.challenge.data;

public class ReportingStructure {
	private Emloyee employee;
	private int numberOfReports;
	
	/**
	 * Constructor
	 */
	public ReportingStructure() {
    }
	
	/**
	 * Getters and setters for the instance variables
	 * @return
	 */
	public Employee getEmployee() {
        return employee;
    }
	
	public Employee setEmployee(Employee employee) {
        this.employee = employee;
    }
	
	public int getNumberOfReports() {
        return numberOfReports;
    }
	
	public int setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
