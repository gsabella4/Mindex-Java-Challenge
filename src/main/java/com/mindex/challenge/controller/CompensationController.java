package com.mindex.challenge.controller;

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
@RequestMapping("/compensation")
public class CompensationController {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Compensation create(@Valid @RequestBody Compensation compensation) {
        LOG.debug("Received compensation create request for [{}]", compensation);

        Compensation newCompensation = compensationService.create(compensation);
        if (newCompensation != null) {
            return newCompensation;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request, insufficient compensation information provided");
        }
    }

    @GetMapping("/{id}")
    public Compensation read(@PathVariable String id) {
        LOG.debug("Received compensation read request for employee id: [{}]", id);

        Compensation compensation = compensationService.read(id);
        if (compensation != null) {
            return compensation;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Employee Id: " + id);
        }
    }

}
