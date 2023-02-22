package com.mindex.challenge.controller;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/compensation") //Shared base path for all handler methods
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @Autowired
    private CompensationRepository compensationRepository;

    //Task 2

    //Creates Compensation and returns new Compensation object
    //Responds with a 201 Created Status code
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Compensation create(@Valid @RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation);

        // Exception handling throwing a HttpStatus.BAD_REQUEST if a compensation record already exists for the Employee
        // Could Modify if record keeping of multiple compensation records for an employee is necessary
        // w/o lines 38-40: Receiving errors in Postman testing when GET request for an employee's compensation when they have multiple Comp records "returned non-unique result"
        if (compensationRepository.findByEmployee(compensation.getEmployee()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is an existing Compensation record for that Employee, please update");
        }
        return compensationService.create(compensation);
    }

    //Returns Compensation object for employeeId
    @GetMapping("/{employeeId}")
    public Compensation read(@PathVariable String employeeId) {
        LOG.debug("Received compensation read request for employee id: [{}]", employeeId);

        Compensation compensation = compensationService.read(employeeId);

        // Exception handling if employeeId is invalid/not found --- implementing as backup as runtimeException in compensationService should catch first
        if (compensation != null) {
            return compensation;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid Employee Id: " + employeeId);
        }
    }
}
