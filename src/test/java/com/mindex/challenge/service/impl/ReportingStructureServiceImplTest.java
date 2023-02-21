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

    private String getReportingStructureByEmployeeIdUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    //Test employee #1 - Ringo Starr - 2 Direct Reports
    private final Employee TEST_EMPLOYEE1 = employeeRepository.findByEmployeeId("03aa1462-ffa9-4978-901b-7c001562cf6f");

    //Test employee #2 - Paul McCartney - No Direct Reports
    private final Employee TEST_EMPLOYEE2 = employeeRepository.findByEmployeeId("b7839309-3348-463b-a7e3-5de1c168beb3");

    //Test employee #3 - John Lennon - 4 Direct Reports
    private final Employee TEST_EMPLOYEE3 = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");

    @Before
    public void setup() {
        getReportingStructureByEmployeeIdUrl = "http://localhost:" + port + "/reportingStructure/{employeeId}";
    }

    //Read Reporting Structure Check - 2 Direct Reports (1 level)
    @Test
    public void testReadReportingStructureTwoReports() {
        ReportingStructure testReportingStructure = new ReportingStructure(TEST_EMPLOYEE1, 2);

        ReportingStructure readReportingStructure = restTemplate.getForEntity(getReportingStructureByEmployeeIdUrl, ReportingStructure.class, TEST_EMPLOYEE1.getEmployeeId()).getBody();

        Assert.assertNotNull(readReportingStructure);
        assertEmployeeEquivalence(TEST_EMPLOYEE1, readReportingStructure.getEmployee());
        Assert.assertEquals(testReportingStructure.getNumberOfReports(), readReportingStructure.getNumberOfReports());
    }

    //Read Reporting Structure Check - No Direct Reports
    @Test
    public void testReadReportingStructureNoReports() {
        ReportingStructure testReportingStructure = new ReportingStructure(TEST_EMPLOYEE2, 0);

        ReportingStructure readReportingStructure = restTemplate.getForEntity(getReportingStructureByEmployeeIdUrl, ReportingStructure.class, TEST_EMPLOYEE2.getEmployeeId()).getBody();

        Assert.assertNotNull(readReportingStructure);
        assertEmployeeEquivalence(TEST_EMPLOYEE2, readReportingStructure.getEmployee());
        Assert.assertEquals(testReportingStructure.getNumberOfReports(), readReportingStructure.getNumberOfReports());
    }

    //Read Reporting Structure Check - 4 Direct Reports (2 levels)
    @Test
    public void testReadReportingStructureFourReports() {
        ReportingStructure testReportingStructure = new ReportingStructure(TEST_EMPLOYEE3, 4);

        ReportingStructure readReportingStructure = restTemplate.getForEntity(getReportingStructureByEmployeeIdUrl, ReportingStructure.class, TEST_EMPLOYEE3.getEmployeeId()).getBody();

        Assert.assertNotNull(readReportingStructure);
        assertEmployeeEquivalence(TEST_EMPLOYEE3, readReportingStructure.getEmployee());
        Assert.assertEquals(testReportingStructure.getNumberOfReports(), readReportingStructure.getNumberOfReports());
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}
