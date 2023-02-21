package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/reportingStructure")
public class ReportingStructureController {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);

    @Autowired
    private ReportingStructureService reportingStructureService;

    //Returns reporting structure for employee, by employeeId
    @GetMapping("/{id}")
    public ReportingStructure read(@PathVariable String id) {
        LOG.debug("Received Reporting Structure read request for employee id [{}]", id);

        ReportingStructure reportingStructure = reportingStructureService.read(id);

        if(reportingStructure != null){
            return reportingStructure;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Employee Id: " + id);
        }
    }
}
