package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    //Creates Employee and returns new Employee object
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee create(@Valid @RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        Employee newEmployee = employeeService.create(employee);
        if (newEmployee != null) {
            return newEmployee;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request, insufficient or invalid Employee information provided");
        }
    }

    //Returns Employee object for employeeId
    @GetMapping("/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee read request for id [{}]", id);

        Employee employee = employeeService.read(id);
        if (employee != null) {
            return employee;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Employee Id: " + id);
        }
    }

    //Updates Employee, returns updated Employee object
    @PutMapping("/{id}")
    public Employee update(@PathVariable String id, @Valid @RequestBody Employee employee) {
        LOG.debug("Received employee update request for id [{}] and employee [{}]", id, employee);

        if (id != employee.getEmployeeId()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee Id provided in path does not match the employee you're attempting to update");
        }

        employee.setEmployeeId(id);

        Employee updatedEmployee = employeeService.update(employee);
        if (updatedEmployee != null) {
            return updatedEmployee;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request, insufficient or invalid Employee information provided");
        }
    }
}
