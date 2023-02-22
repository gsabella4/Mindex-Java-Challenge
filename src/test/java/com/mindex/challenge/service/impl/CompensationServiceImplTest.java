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

    private String compensationUrl;
    private String compensationIdUrl;

    @Autowired
    private CompensationService compensationService;

    @Autowired
    private EmployeeServiceImpl employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation";
        compensationIdUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    // Compensation Create and Read - Test #1
    @Test
    public void testOneCreateReadCompensation() {
        // testEmployee - John Lennon
        Employee testEmployee = employeeService.read("b7839309-3348-463b-a7e3-5de1c168beb3");
        Compensation testCompensation = new Compensation(testEmployee, BigDecimal.valueOf(80000), LocalDate.of(2023, 3, 6));

        // Create checks
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        Assert.assertNotNull(createdCompensation);
        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, testEmployee.getEmployeeId()).getBody();

        Assert.assertNotNull(readCompensation);
        assertCompensationEquivalence(testCompensation, readCompensation);
    }

    // Compensation Create and Read - Test #2
    @Test
    public void testTwoCreateReadCompensation() {
        // testEmployee - Ringo Starr
        Employee testEmployee = employeeService.read("03aa1462-ffa9-4978-901b-7c001562cf6f");
        Compensation testCompensation = new Compensation(testEmployee, BigDecimal.valueOf(135000), LocalDate.of(2023, 4, 3));

        // Create checks
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        Assert.assertNotNull(createdCompensation);
        assertCompensationEquivalence(testCompensation, createdCompensation);

        // Read checks
        Compensation readCompensation = restTemplate.getForEntity(compensationIdUrl, Compensation.class, testEmployee.getEmployeeId()).getBody();

        Assert.assertNotNull(readCompensation);
        assertCompensationEquivalence(testCompensation, readCompensation);
    }

    // Helper method to ensure Employee Object Equivalence
    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

    // Helper method to ensure Compensation Object Equivalence
    private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
        Assert.assertEquals(expected.getSalary().compareTo(actual.getSalary()), 0);
        Assert.assertEquals(expected.getEffectiveDate().compareTo(actual.getEffectiveDate()), 0);
        assertEmployeeEquivalence(expected.getEmployee(), actual.getEmployee());
    }
}
