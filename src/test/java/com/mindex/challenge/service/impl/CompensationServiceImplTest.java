package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    private String createCompensationUrl;
    private String getCompensationByEmployeeIdUrl;


    @Autowired
    private CompensationService compensationService;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    //Test employee #1 - Ringo Starr
    private final Employee TEST_EMPLOYEE1 = employeeService.read("03aa1462-ffa9-4978-901b-7c001562cf6f");

    //Test employee #2 - Paul McCartney
    private final Employee TEST_EMPLOYEE2 = employeeService.read("b7839309-3348-463b-a7e3-5de1c168beb3");

    @Before
    public void setup() {
        createCompensationUrl = "http://localhost:" + port + "/compensation";
        getCompensationByEmployeeIdUrl = "http://localhost:" + port + "/compensation/{employeeId}";
    }

    //Create Compensation Check
    @Test
    public void testCreateCompensation() {
        Compensation testCompensation = new Compensation(TEST_EMPLOYEE1, BigDecimal.valueOf(130000), LocalDate.of(2023, 3, 1));

        Compensation createdCompensation = restTemplate.postForEntity(createCompensationUrl, testCompensation, Compensation.class).getBody();

        Assert.assertNotNull(createdCompensation);
        //Comparing BigDecimal Salary, will return 0 if matching
        Assert.assertEquals(createdCompensation.getSalary().compareTo(testCompensation.getSalary()), 0);
        //Comparing LocalDate effectiveDate, will return 0 if matching
        Assert.assertEquals(createdCompensation.getEffectiveDate().compareTo(testCompensation.getEffectiveDate()), 0);
        assertEmployeeEquivalence(createdCompensation.getEmployee(), testCompensation.getEmployee());
    }

    //Read Compensation Check
    @Test
    public void testReadCompensation() {
        Compensation testCompensation = new Compensation(TEST_EMPLOYEE2, BigDecimal.valueOf(80000), LocalDate.of(2023, 3, 1));

        Compensation readCompensation = restTemplate.getForEntity(getCompensationByEmployeeIdUrl, Compensation.class, TEST_EMPLOYEE2.getEmployeeId()).getBody();

        Assert.assertNotNull(readCompensation);
        //Comparing BigDecimal Salary, will return 0 if matching
        Assert.assertEquals(readCompensation.getSalary().compareTo(testCompensation.getSalary()), 0);
        //Comparing LocalDate effectiveDate, will return 0 if matching
        Assert.assertEquals(readCompensation.getEffectiveDate().compareTo(testCompensation.getEffectiveDate()), 0);
        assertEmployeeEquivalence(readCompensation.getEmployee(), testCompensation.getEmployee());
    }

    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
