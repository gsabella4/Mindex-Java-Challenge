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

    //Task 1

    //Returns reporting structure for employee, by employeeId
    //If employeeId is not valid and/or not found, throws a runtime exception
    @Override
    public ReportingStructure read(String id) {
        LOG.debug("Creating reporting structure for employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);


        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        int numOfDirectReports = 0;

        List<Employee> directReports = employee.getDirectReports();
        if (directReports != null) {
            for (Employee reportingEmployee : directReports) {
                if (reportingEmployee.getDirectReports() != null) {
                    numOfDirectReports += 1 + getNumberOfReports(reportingEmployee.getEmployeeId());
                } else {
                    numOfDirectReports++;
                }
            }
        }
        reportingStructure.setNumberOfReports(numOfDirectReports);

        return reportingStructure;
    }

    public int getNumberOfReports(String id){
        Employee employee = employeeService.read(id);
        if (employee.getDirectReports() != null) {
            return employee.getDirectReports().size();
        } else {
            return 0;
        }
    }
}
