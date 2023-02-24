package com.mindex.challenge.controller;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        // Exception handling if Employee Record already exists, please utilize the Update method
        if (employeeRepository.findByEmployeeId(employee.getEmployeeId()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is an existing Employee record for that Employee, please update");
        }
        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee create request for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{employeeId}")
    public Employee update(@PathVariable String employeeId, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", employeeId, employee);

        employee.setEmployeeId(employeeId);
        return employeeService.update(employee);
    }
}
