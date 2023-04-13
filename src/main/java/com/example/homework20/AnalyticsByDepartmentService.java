package com.example.homework20;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnalyticsByDepartmentService {
    public List<Employee> getListDepartmentEmployees(int departmentId) {
        if (!EmployeeService.departmentNumbers.contains(departmentId)) {
            throw new EmployeeBadParameters("неверно указан отдел!");
        }
        List<Employee> employeeList = new ArrayList<>(EmployeeService.employees.values());
        return employeeList.stream().filter(e -> e.getDepartmentId() == departmentId).collect(Collectors.toList());
    }

    public Employee getEmployeeWithMaxSalaryInDepartment(int departmentId) {
        if (!EmployeeService.departmentNumbers.contains(departmentId)) {
            throw new EmployeeBadParameters("неверно указан отдел!");
        }
        List<Employee> employeeList = new ArrayList<>(EmployeeService.employees.values());
        Optional<Employee> result = employeeList.stream().filter(employee -> employee.getDepartmentId() == departmentId)
                .max(Comparator.comparingDouble(Employee::getSalary));
        result.orElseThrow(() -> new EmployeeNotFoundException("искомый сотрудник не найден"));
        return result.get();
    }

    public Employee getEmployeeWithMinSalaryInDepartment(int departmentId) {
        if (!EmployeeService.departmentNumbers.contains(departmentId)) {
            throw new EmployeeBadParameters("неверно указан отдел!");
        }
        List<Employee> employeeList = new ArrayList<>(EmployeeService.employees.values());
        Optional<Employee> result = employeeList.stream().filter(employee -> employee.getDepartmentId() == departmentId)
                .min(Comparator.comparingDouble(Employee::getSalary));
        result.orElseThrow(() -> new EmployeeNotFoundException("искомый сотрудник не найден"));
        return result.get();
    }
}
