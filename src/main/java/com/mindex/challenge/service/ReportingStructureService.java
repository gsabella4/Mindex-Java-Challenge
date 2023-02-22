package com.mindex.challenge.service;

import com.mindex.challenge.data.ReportingStructure;

public interface ReportingStructureService {
    ReportingStructure read(String employeeId);
    int getNumberOfReports(String employeeId);
}
