package com.example.homework20;

import java.util.Objects;

public class Employee {
    static int nextId = 1;
    final private int ID;
    final private int passportNumber;
    private String firstName, middleName, lastName;
    private int departmentId;
    private int salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        if (getPassportNumber() == employee.passportNumber) return true;
        return getFirstName().equals(employee.getFirstName()) && getMiddleName().equals(employee.getMiddleName()) && getLastName().equals(employee.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassportNumber(), getFirstName(), getMiddleName(), getLastName());
    }

    public Employee(int passportNumber, String firstName, String middleName, String lastName, int departmentId, int salary) {
        if (passportNumber < 1) {
            throw new EmployeeBadParameters("неверно указаны номер паспорта сотрудника!");
        }
        if ("".equals(firstName.trim()) || "".equals(lastName.trim()) || "".equals(middleName.trim())) {
            throw new EmployeeBadParameters("неверно указаны фамилия имя отчество сотрудника!");
        }
        if (!EmployeeService.departmentNumbers.contains(departmentId)) {
            throw new EmployeeBadParameters("неверно указан отдел!");
        }
        if (salary < 1) {
            throw new EmployeeBadParameters("неверно указан размер заработной платы!");
        }
        this.passportNumber = passportNumber;
        this.firstName = firstName.trim();
        this.middleName = middleName.trim();
        this.lastName = lastName.trim();
        this.departmentId = departmentId;
        this.salary = salary;
        this.ID = nextId;
    }

    public int getID() {
        return ID;
    }

    public int getPassportNumber() {
        return passportNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public int getSalary() {
        return salary;
    }

    public void setFirstName(String firstName) {
        if ("".equals(firstName.trim())) {
            throw new EmployeeBadParameters("неверно указано имя сотрудника!");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if ("".equals(lastName.trim())) {
            throw new EmployeeBadParameters("неверно указана фамилия сотрудника!");
        }
        this.lastName = lastName;
    }

    public void setDepartmentId(int departmentId) {
        if (!EmployeeService.departmentNumbers.contains(departmentId)) {
            throw new EmployeeBadParameters("неверно указан отдел!");
        }
        this.departmentId = departmentId;
    }

    public void setSalary(int salary) {
        if (salary <= 0) {
            throw new EmployeeBadParameters("неверно указан размер заработной платы!");
        }
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "номер паспорта " + passportNumber + ", " +
                lastName + " " +
                firstName + " " +
                middleName + " " +
                ", табельный номер " + ID +
                ", отдел №" + departmentId +
                ", оклад " + salary + " руб.";
    }
}
