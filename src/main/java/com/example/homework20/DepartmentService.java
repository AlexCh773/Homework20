package com.example.homework20;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final EmployeeService employeeService;

    public DepartmentService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private void checkDepartmentId(int departmentId) {
        if (!EmployeeService.departmentNumbers.contains(departmentId)) {
            throw new EmployeeBadParameters("неверно указан отдел!");
        }
    }
    public List<Employee> getListDepartmentEmployees(int departmentId) {
        checkDepartmentId(departmentId);
        List<Employee> employeeList = new ArrayList<>(employeeService.getAll());
        return employeeList.stream().filter(e -> e.getDepartmentId() == departmentId).collect(Collectors.toList());
    }

    public Employee getEmployeeWithMaxSalaryInDepartment(int departmentId) {
        checkDepartmentId(departmentId);
        List<Employee> employeeList = new ArrayList<>(employeeService.getAll());
        Optional<Employee> result = employeeList.stream().filter(employee -> employee.getDepartmentId() == departmentId).max(Comparator.comparingDouble(Employee::getSalary));
        result.orElseThrow(() -> new EmployeeNotFoundException("искомый сотрудник не найден"));
        return result.get();
    }

    public Employee getEmployeeWithMinSalaryInDepartment(int departmentId) {
        checkDepartmentId(departmentId);
        List<Employee> employeeList = new ArrayList<>(employeeService.getAll());
        Optional<Employee> result = employeeList.stream().filter(employee -> employee.getDepartmentId() == departmentId).min(Comparator.comparingDouble(Employee::getSalary));
        result.orElseThrow(() -> new EmployeeNotFoundException("искомый сотрудник не найден"));
        return result.get();
    }

    public int getSumDepartmentSalary(int departmentId) {
        checkDepartmentId(departmentId);
        List<Employee> employeeList = new ArrayList<>(employeeService.getAll());
        return employeeList.stream().filter(employee -> employee.getDepartmentId() == departmentId).mapToInt(Employee::getSalary).sum();
    }

    public Map<Integer, List<Employee>> getMapEmployeesSortedByDepartments() {
        List<Employee> employeeList = new ArrayList<>(employeeService.getAll());
        Map<Integer, List<Employee>> resultMap = new HashMap<>();
        for (Integer departmentId : EmployeeService.departmentNumbers) {
            List<Employee> employees =
                    employeeList.stream()
                            .filter(employee -> employee.getDepartmentId() == departmentId)
                            .collect(Collectors.toList());
            resultMap.put(departmentId, employees);
        }
        return resultMap;
    }
}
