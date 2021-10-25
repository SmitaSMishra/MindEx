package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }
    /**
     * Method to readREporingStructure
     * @param id The employee ID
     * @return The ReportingStructure object
     */
    @Override
    public ReportingStructure readReportingStructure(String id) {
    	LOG.debug("Reading ReportingStructures for employee [{}]",employeeId);
    	 //Get employee by id and add to ReportingStructure
    	Employee employee = employeeRepository.findByEmployeeId(id);
    	ReportingStructure reportingStructure = new ReportingStructure();
    	reportingStructure.setEmployee(employee);
    	
    	//Get the immidiate Direct Reports and add to Reporting structure
    	int numberOfReports = employee.getDirectReports().size();
    	Set<String> drIds = new HashSet<>();
    	//Get their DirectReports and add the ids to a set
    	for dr : employee.getDirectReports(){
    		for drDist : dr.getDirectReports().stream().distinct(){
    			drIds.add(drDist.employee.getEmployeeId());
    		}
    	}
    	//Count the number of distinct ids.
    	numberOfReports += drIds.size();
    	reportingStructure.setNumberOfReports(numberOfReports);
    	if(reportingStructure == null) {
    		throw new RuntimeException("Invalid employeeId: " + id);
    	}
    	
    	return reportingStructure;
    }
}
