package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String getReportingStructureIdUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        getReportingStructureIdUrl = "http://localhost:" + port + "/reportingStructure/{id}";
    }

    //Read Reporting Structure - Test #1 - 2 Direct Reports (1 level of reports)
    @Test
    public void testOneReadReportingStructure() {
        // testEmployee - Ringo Starr
        Employee testEmployee = employeeRepository.findByEmployeeId("03aa1462-ffa9-4978-901b-7c001562cf6f");
        ReportingStructure testReportingStructure = new ReportingStructure(testEmployee, 2);

        // Read checks
        ReportingStructure readReportingStructure = restTemplate.getForEntity(getReportingStructureIdUrl, ReportingStructure.class, testEmployee.getEmployeeId()).getBody();

        Assert.assertNotNull(readReportingStructure);
        assertReportingStructureEquivalence(testReportingStructure, readReportingStructure);
    }

    //Read Reporting Structure - Test #2 - No Direct Reports
    @Test
    public void testTwoReadReportingStructure() {
        // testEmployee - Paul McCartney
        Employee testEmployee = employeeRepository.findByEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb3");
        ReportingStructure testReportingStructure = new ReportingStructure(testEmployee, 0);

        // Read checks
        ReportingStructure readReportingStructure = restTemplate.getForEntity(getReportingStructureIdUrl, ReportingStructure.class, testEmployee.getEmployeeId()).getBody();

        Assert.assertNotNull(readReportingStructure);
        assertReportingStructureEquivalence(testReportingStructure, readReportingStructure);
    }

    //Read Reporting Structure - Test #3- 4 Direct Reports (2 levels of reports)
    @Test
    public void testThreeReadReportingStructure() {
        // testEmployee - John Lennon
        Employee testEmployee = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
        ReportingStructure testReportingStructure = new ReportingStructure(testEmployee, 4);

        // Read checks
        ReportingStructure readReportingStructure = restTemplate.getForEntity(getReportingStructureIdUrl, ReportingStructure.class, testEmployee.getEmployeeId()).getBody();

        Assert.assertNotNull(readReportingStructure);
        assertReportingStructureEquivalence(testReportingStructure, readReportingStructure);
    }

    // Helper method to ensure Employee Object Equivalence
    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

    // Helper method to ensure Reporting Structure Equivalence
    private static void assertReportingStructureEquivalence(ReportingStructure expected, ReportingStructure actual) {
        assertEmployeeEquivalence(expected.getEmployee(), actual.getEmployee());
        Assert.assertEquals(expected.getNumberOfReports(), actual.getNumberOfReports());
    }
}
