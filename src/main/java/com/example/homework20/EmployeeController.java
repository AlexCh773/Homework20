package com.example.homework20;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/add")
    public Object addEmployee(@RequestParam(required = false)
                              Integer passportNumber,
                              String firstName,
                              String middleName,
                              String lastName,
                              Integer departmentNumber,
                              Integer salary) {
        try {
            checkParameters(passportNumber, firstName, middleName, lastName, departmentNumber, salary);
            return "сотрудник успешно добален: " + employeeService.addEmployee(passportNumber, firstName, middleName, lastName, departmentNumber, salary);
        } catch (EmployeeBadParameters | EmployeeAlreadyAddedException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/remove")
    public Object removeEmployee(@RequestParam(required = false) Integer passportNumber) {
        if (passportNumber == null) {
            return "не задан номер паспорта сотрудника!";
        }
        try {
            return "сотрудник успешно удален: " + employeeService.removeEmployee(passportNumber);
        } catch (EmployeeBadParameters | EmployeeNotFoundException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/find")
    public Object findEmployee(@RequestParam(required = false) Integer passportNumber) {
        if (passportNumber == null) {
            return "не задан номер паспорта сотрудника!";
        }
        try {
            return employeeService.findEmployeeByPassport(passportNumber);
        } catch (EmployeeNotFoundException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/listEmployees")
    public Object getListEmployees() {
        return employeeService.employees;
    }

    public void checkParameters(Object... params) {
        for (Object param : params) {
            if (param == null) {
                throw new EmployeeBadParameters("неверно заданы параметры!");
            }
        }
    }
}
