package com.mindex.challenge.data;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class Employee {

    @NotBlank(message = "employeeId field cannot be blank")
    private String employeeId;
    @NotBlank(message = "firstName field cannot be blank")
    private String firstName;
    @NotBlank(message = "lastName field cannot be blank")
    private String lastName;
    @NotBlank(message = "position field cannot be blank")
    private String position;
    @NotBlank(message = "department field cannot be blank")
    private String department;
    private List<Employee> directReports;

    public Employee() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Employee> getDirectReports() {
        return directReports;
    }

    public void setDirectReports(List<Employee> directReports) {
        this.directReports = directReports;
    }
}
