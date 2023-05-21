package com.example.homework20;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/{id}/salary/sum")
    public Object getSumDepartmentSalary(@PathVariable Integer id) {
        if (id == null) {
            return "не задан номер отдела!";
        }
        Object result;
        try {
            result = departmentService.getSumDepartmentSalary(id);
        } catch (EmployeeBadParameters e) {
            result = e.getMessage();
        }
        return result;
    }

    @GetMapping("/{id}/salary/max")
    public Object getEmployeeWithMaxSalaryInDepartment(@PathVariable Integer id) {
        if (id == null) {
            return "не задан номер отдела!";
        }
        Object result;
        try {
            result = departmentService.getEmployeeWithMaxSalaryInDepartment(id);
        } catch (EmployeeNotFoundException | EmployeeBadParameters e) {
            result = e.getMessage();
        }
        return result;
    }

    @GetMapping("/{id}/salary/min")
    public Object getEmployeeWithMinSalaryInDepartment(@PathVariable Integer id) {
        if (id == null) {
            return "не задан номер отдела!";
        }
        Object result;
        try {
            result = departmentService.getEmployeeWithMinSalaryInDepartment(id);
        } catch (EmployeeNotFoundException | EmployeeBadParameters e) {
            result = e.getMessage();
        }
        return result;
    }

    @GetMapping("/{id}/employees")
    public Object getListDepartmentEmployees(@PathVariable Integer id) {
        if (id == null) {
            return "не задан номер отдела!";
        }
        Object result;
        try {
            result = departmentService.getListDepartmentEmployees(id);
        } catch (EmployeeBadParameters e) {
            result = e.getMessage();
        }
        System.out.println(result);
        return result;
    }

    @GetMapping("/employees")
    public Map<Integer, List<Employee>> getMapEmployeesSortedByDepartments() {
        return departmentService.getMapEmployeesSortedByDepartments();
    }
}
