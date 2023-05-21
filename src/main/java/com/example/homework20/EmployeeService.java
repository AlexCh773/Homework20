package com.example.homework20;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {
    final public static List<Integer> departmentNumbers = new ArrayList<>(Set.of(1, 2, 3, 4, 5));

    static {
        departmentNumbers.sort(Integer::compareTo);
    }

    public static Map<String, Employee> employees = new HashMap<>();

    public Employee addEmployee(int passportNumber, String firstName, String middleName, String lastName, int departmentId, int salary) {
        Employee employee = new Employee(passportNumber, firstName, middleName, lastName, departmentId, salary);
        if (employees.containsValue(employee)) {
            throw new EmployeeAlreadyAddedException("такой сотрудник уже есть");
        }
        employees.put(Integer.toString(employee.getPassportNumber()), employee);
        Employee.nextId++;
        return employee;
    }

    public Employee removeEmployee(Integer passportNumber) {
        Employee employee = employees.remove(passportNumber.toString());
        if (employee == null) {
            throw new EmployeeNotFoundException("удаляемый сотрудник не найден");
        }
        return employee;
    }

    public Employee findEmployeeByPassport(Integer passportNumber) {
        Employee employee = employees.get(passportNumber.toString());
        if (employee == null) {
            throw new EmployeeNotFoundException("искомый сотрудник не найден");
        }
        return employee;
    }

    public Collection<Employee> getAll() {
        return employees.values();
    }
}
