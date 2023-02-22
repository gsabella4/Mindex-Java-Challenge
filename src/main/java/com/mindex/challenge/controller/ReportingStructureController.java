package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;

    //Task 1

    //Returns reporting structure for employee, by employeeId
    @GetMapping("/reportingStructure/{id}")
    public ReportingStructure read(@PathVariable String id) {
        LOG.debug("Received Reporting Structure read request for employee id [{}]", id);

        ReportingStructure reportingStructure = reportingStructureService.read(id);

        // Exception handling if employeeId is invalid/not found --- implementing as backup as runtimeException in reportingStructureService should catch first
        if(reportingStructure != null){
            return reportingStructure;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Employee Id: " + id);
        }
    }
}
