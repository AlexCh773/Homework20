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
            checkParametersForNull(passportNumber, firstName, middleName, lastName, departmentNumber, salary);
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

    public void checkParametersForNull(Object... params) {
        for (Object param : params) {
            if (param == null) {
                throw new EmployeeBadParameters("неверно заданы параметры!");
            }
        }
    }

    @GetMapping("/departments/max-salary")
    public Object getEmployeeWithMaxSalaryInDepartment(@RequestParam(required = false) Integer departmentId) {
        if (departmentId == null) {
            return "не задан номер отдела!";
        }
        Object result;
        try {
            result = employeeService.getEmployeeWithMaxSalaryInDepartment(departmentId);
        } catch (EmployeeNotFoundException | EmployeeBadParameters e) {
            result = e.getMessage();
        }
        return result;
    }

    @GetMapping("/departments/min-salary")
    public Object getEmployeeWithMinSalaryInDepartment(@RequestParam(required = false) Integer departmentId) {
        if (departmentId == null) {
            return "не задан номер отдела!";
        }
        Object result;
        try {
            result = employeeService.getEmployeeWithMinSalaryInDepartment(departmentId);
        } catch (EmployeeNotFoundException | EmployeeBadParameters e) {
            result = e.getMessage();
        }
        return result;
    }

    @GetMapping(path = "/departments/all", params = "departmentId")
    public Object getListDepartmentEmployees(@RequestParam(required = false) Integer departmentId) {
        if (departmentId == null) {
            return "не задан номер отдела!";
        }
        Object result;
        try {
            result = employeeService.getListDepartmentEmployees(departmentId);
        } catch (EmployeeBadParameters e) {
            result = e.getMessage();
        }
        System.out.println(result);
        return result;
    }

    @GetMapping("/departments/all")
    public StringBuilder getListEmployeesSortedByDepartments() {
        StringBuilder result = new StringBuilder("");
        for (Integer departmentId : EmployeeService.departmentNumbers) {
            result.append("отдел №").append(departmentId).append(":");
            result.append(employeeService.getListDepartmentEmployees(departmentId)).append("<br>");
        }
        return result;
    }
}
