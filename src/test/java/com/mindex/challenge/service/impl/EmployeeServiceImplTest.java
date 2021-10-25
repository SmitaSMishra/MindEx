package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceImplTest {

	private String employeeUrl;
	private String employeeIdUrl;
	private String reportingStructureUrl;

	@Autowired
	private EmployeeService employeeService;
	private CompensationService compensationService;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setup() {
		employeeUrl = "http://localhost:" + port + "/employee";
		employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
		reportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{id}";
	}

	@Test
	public void testCreateReadUpdate() {
		Employee testEmployee = new Employee();
		testEmployee.setFirstName("John");
		testEmployee.setLastName("Doe");
		testEmployee.setDepartment("Engineering");
		testEmployee.setPosition("Developer");

		// Create checks
		Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

		assertNotNull(createdEmployee.getEmployeeId());
		assertEmployeeEquivalence(testEmployee, createdEmployee);

		// Read checks
		Employee readEmployee = restTemplate
				.getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();
		assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
		assertEmployeeEquivalence(createdEmployee, readEmployee);

		// Update checks
		readEmployee.setPosition("Development Manager");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		Employee updatedEmployee = restTemplate.exchange(employeeIdUrl, HttpMethod.PUT,
				new HttpEntity<Employee>(readEmployee, headers), Employee.class, readEmployee.getEmployeeId())
				.getBody();

		assertEmployeeEquivalence(readEmployee, updatedEmployee);
	}

	/**
	 * Test case for getting the ReportingStructure.
	 */
	@Test
	public void testReportingStructure() {
		//Adding employees in a tree strcture
		Employee testEmployee = new Employee();
		testEmployee.setFirstName("Seed");
		testEmployee.setLastName("Sundaram");
		testEmployee.setDepartment("Engineering");
		testEmployee.setPosition("Quality");

		Employee testEmployee1 = new Employee();
		testEmployee1.setFirstName("John");
		testEmployee1.setLastName("Doe");
		testEmployee1.setDepartment("Engineering");
		testEmployee1.setPosition("Developer");

		Employee testEmployee2 = new Employee();
		testEmployee2.setFirstName("Amareetha");
		testEmployee2.setLastName("Menon");
		testEmployee2.setDepartment("Engineering");
		testEmployee2.setPosition("Developer");

		Employee testEmployee3 = new Employee();
		testEmployee3.setFirstName("Lahari");
		testEmployee3.setLastName("Chepuri");
		testEmployee3.setDepartment("Engineering");
		testEmployee3.setPosition("Developer");

		Employee testEmployee4 = new Employee();
		testEmployee4.setFirstName("Chandler");
		testEmployee4.setLastName("Bing");
		testEmployee4.setDepartment("Engineering");
		testEmployee4.setPosition("Transponster");

		Employee testEmployee5 = new Employee();
		testEmployee5.setFirstName("Ross");
		testEmployee5.setLastName("Gellar");
		testEmployee5.setDepartment("Engineering");
		testEmployee5.setPosition("Paleantalogist");

		testEmployee.setDirectReports(testEmployee1);
		testEmployee.setDirectReports(testEmployee2);
		testEmployee1.setDirectReports(testEmployee3);
		testEmployee1.setDirectReports(testEmployee4);

		// Create checks
		Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();

		assertNotNull(createdEmployee.getEmployeeId());
		assertEmployeeEquivalence(testEmployee, createdEmployee);

		// Read ReportingStructure checks
		ReportingStructure readReportingStructure = restTemplate
				.getForEntity(reportingStructureUrl, ReportingStructure.class, createdEmployee.getEmployeeId())
				.getBody();
		assertEquals(createdEmployee.getEmployeeId(), readReportingStructure.getEmployee().getEmployeeId());
		assertReportingStructureEquivalence(createdEmployee, readReportingStructure);
	}

	/**
	 * Assertions for Reporting Structure field results.
	 * @param expected
	 * @param actual
	 */
	private static void assertReportingStructureEquivalence(Employee expected, Employee actual) {
    	assertEquals(expected.getFirstName(), actual.getEmployee().getFirstName());
        assertEquals(expected.getLastName(), actual.getEmployee().getLastName());
        assertEquals(expected.getDepartment(), actual.getEmployee().getDepartment());
        assertEquals(expected.getPosition(), actual.getEmployee()l.getPosition());
        assertEquals(4,actual.getNumberOfReports(expected.getEmployeeId()));
	}

	private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getDepartment(), actual.getDepartment());
		assertEquals(expected.getPosition(), actual.getPosition());
	}
}
