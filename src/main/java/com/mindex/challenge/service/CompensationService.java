package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

public interface CompensationService {
    //Task 2
    Compensation create(Compensation compensation);
    Compensation read(String employeeId);
}
