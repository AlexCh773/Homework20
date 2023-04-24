package com.example.homework20;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee/departments/")
public class AnalyticsByDepartmentController {
    private final AnalyticsByDepartmentService analyticsService;

    public AnalyticsByDepartmentController(AnalyticsByDepartmentService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/max-salary")
    public Object getEmployeeWithMaxSalaryInDepartment(@RequestParam(required = false) Integer departmentId) {
        if (departmentId == null) {
            return "не задан номер отдела!";
        }
        Object result;
        try {
            result = analyticsService.getEmployeeWithMaxSalaryInDepartment(departmentId);
        } catch (EmployeeNotFoundException | EmployeeBadParameters e) {
            result = e.getMessage();
        }
        return result;
    }

    @GetMapping("/min-salary")
    public Object getEmployeeWithMinSalaryInDepartment(@RequestParam(required = false) Integer departmentId) {
        if (departmentId == null) {
            return "не задан номер отдела!";
        }
        Object result;
        try {
            result = analyticsService.getEmployeeWithMinSalaryInDepartment(departmentId);
        } catch (EmployeeNotFoundException | EmployeeBadParameters e) {
            result = e.getMessage();
        }
        return result;
    }

    @GetMapping(path = "/all", params = "departmentId")
    public Object getListDepartmentEmployees(@RequestParam(required = false) Integer departmentId) {
        if (departmentId == null) {
            return "не задан номер отдела!";
        }
        Object result;
        try {
            result = analyticsService.getListDepartmentEmployees(departmentId);
        } catch (EmployeeBadParameters e) {
            result = e.getMessage();
        }
        System.out.println(result);
        return result;
    }

    @GetMapping("/all")
    public StringBuilder getListEmployeesSortedByDepartments() {
        StringBuilder result = new StringBuilder("");
        for (Integer departmentId : EmployeeService.departmentNumbers) {
            result.append("отдел №").append(departmentId).append(":");
            result.append(analyticsService.getListDepartmentEmployees(departmentId)).append("<br>");
        }
        return result;
    }
}
