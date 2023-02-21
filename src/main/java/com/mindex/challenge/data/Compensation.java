package com.mindex.challenge.data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Compensation {

    //Task 2

    //Compensation Properties
    @NotNull(message = "The Employee object cannot be null")
    private Employee employee;
    @Positive
    private BigDecimal salary;
    @Future(message = "The Effective Date must be a future date")
    private LocalDate effectiveDate;

    //Constructors for Compensation
    public Compensation(){}

    public Compensation(Employee employee, BigDecimal salary, LocalDate effectiveDate) {
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    //Getters and Setters
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
