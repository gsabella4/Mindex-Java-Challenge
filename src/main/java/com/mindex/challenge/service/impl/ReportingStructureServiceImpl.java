package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeServiceImpl employeeService;

    // Task 1

    // Returns reporting structure for employee, by employee id
    // If employeeId is not valid and/or not found, throws a runtime exception
    @Override
    public ReportingStructure read(String employeeId) {
        LOG.debug("Creating reporting structure for employee with id [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        int numOfDirectReports = 0;

        List<Employee> directReports = employee.getDirectReports();
        if (directReports != null) {
            for (Employee reportingEmployee : directReports) {
                    numOfDirectReports += 1 + getNumberOfReports(reportingEmployee.getEmployeeId());
            }
        }
        reportingStructure.setNumberOfReports(numOfDirectReports);
        return reportingStructure;
    }

    // Returns number of direct reports for a given employee id
    @Override
    public int getNumberOfReports(String employeeId){
        LOG.debug("Retrieving Count of Direct Reports for employee with id [{}]", employeeId);

        Employee employee = employeeService.read(employeeId);
        int numOfDirectReports = 0;

        List<Employee> directReports = employee.getDirectReports();
        if (directReports != null) {
            for (Employee reportingEmployee : directReports) {
                numOfDirectReports++;
            }
        }
        return numOfDirectReports;
    }
}
