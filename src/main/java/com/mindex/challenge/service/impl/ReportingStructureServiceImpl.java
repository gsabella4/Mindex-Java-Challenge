package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    //Returns reporting structure for employee, by employeeId
    //If employeeId is not valid and/or not found, throws a runtime exception
    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Creating reporting structure for employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);
        ReportingStructure reportingStructure;

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

            List<Employee> directReports = employee.getDirectReports();
            int numOfDirectReports = directReports.size();

            if (numOfDirectReports > 0) {
                for (Employee each : directReports) {
                    if (each.getDirectReports().size() > 0) {
                        numOfDirectReports += each.getDirectReports().size();
                    }
                }
            }

            reportingStructure = new ReportingStructure(employee, numOfDirectReports);

        return reportingStructure;
    }
}
