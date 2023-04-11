package com.example.homework20;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class EmployeeService {
    public static Set<Integer> DepartmentNumbers = Set.of(1, 2, 3, 4, 5);

    public Map<Integer, Employee> employees = new HashMap<>();

    public Employee addEmployee(int passportNumber, String firstName, String middleName, String lastName, int departmentNumber, int salary) {
        Employee employee = new Employee(passportNumber, firstName, middleName, lastName, departmentNumber, salary);
        if (employees.containsValue(employee)) {
            throw new EmployeeAlreadyAddedException("такой сотрудник уже есть");
        }
        employees.put(employee.getPassportNumber(), employee);
        Employee.nextId++;
        return employee;
    }

    public Employee removeEmployee(int passportNumber) {
        Employee employee = employees.remove(passportNumber);
        if (employee == null) {
            throw new EmployeeNotFoundException("удаляемый сотрудник не найден");
        }
        return employee;
    }

    public Employee findEmployeeByPassport(int passportNumber) {
        Employee employee = employees.get(passportNumber);
        if (employee == null) {
            throw new EmployeeNotFoundException("искомый сотрудник не найден");
        }
        return employee;
    }

}
