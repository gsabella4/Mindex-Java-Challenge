package com.mindex.challenge.data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;

public class Compensation {

    private Employee employee;
    @Positive
    @NotBlank(message = "salary field must not be blank")
    private double salary;
    @Future(message = "The Effective Date must be a future date")
    @NotBlank(message = "effectiveDate field must not be blank")
    private Date effectiveDate;

    //Constructors for Compensation
    public Compensation(){}

    public Compensation(Employee employee, double salary, Date effectiveDate) {
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
