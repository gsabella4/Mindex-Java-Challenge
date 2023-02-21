package com.mindex.challenge.data;

import javax.validation.constraints.NotNull;

public class ReportingStructure {

    //Task 1

    //ReportingStructure Properties
    @NotNull(message = "The Employee object cannot be null")
    private Employee employee;
    private int numberOfReports;

    //Constructors for ReportingStructure
    public ReportingStructure() {}

    public ReportingStructure(Employee employee, int numberOfReports) {
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    //Getters and Setters
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
}
