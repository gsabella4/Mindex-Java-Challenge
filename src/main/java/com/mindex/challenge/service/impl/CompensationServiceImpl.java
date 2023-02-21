package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    @Autowired
    private CompensationRepository compensationRepository;

    //Creates Compensation and returns new Compensation object
    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);

        compensationRepository.insert(compensation);
        return compensation;
    }

    //Returns Compensation object for employee, by employeeId
    //If employeeId is not valid and/or not found, throws a runtime exception
    @Override
    public Compensation read(String id) {
        LOG.debug("Reading compensation for employee Id: [{}]", id);

        Compensation compensation = compensationRepository.findByEmployeeId(id);

        if(compensation == null){
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return compensation;
    }
}
