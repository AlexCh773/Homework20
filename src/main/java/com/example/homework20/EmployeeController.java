package com.example.homework20;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import  org.apache.commons.lang3.StringUtils;

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
            checkParametersForNull(passportNumber, firstName, middleName, lastName, departmentNumber, salary);
            checkNameForValidity(firstName, middleName, lastName);
            return "сотрудник успешно добален: " + employeeService.addEmployee(passportNumber, firstName, middleName, lastName, departmentNumber, salary);
        } catch (EmployeeAlreadyAddedException e) {
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
    private Object getListEmployees() {
        return EmployeeService.employees;
    }

    public void checkParametersForNull(Object... params) {
        for (Object param : params) {
            if (param == null) {
                throw new EmployeeBadParameters();
            }
        }
    }

    private void checkNameForValidity(String... strings) {
        for (String string : strings) {
            if (!StringUtils.isAlpha(string)) {
                throw new EmployeeBadParameters();
            }
        }
    }
}
