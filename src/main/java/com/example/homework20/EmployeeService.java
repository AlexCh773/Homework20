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

    {
        employees.put("1423", new Employee(1423, "Ivan", "Petrovich", "Sidorov", 3, 110000));
        employees.put("2521", new Employee(2521, "Petr", "Ivanovich", "Petrov", 1, 100000));
        employees.put("8452", new Employee(8452, "Ignat", "Andreevich", "Ivanov", 2, 120000));
        employees.put("1923", new Employee(1923, "Pavel", "Petrovich", "Krutov", 1, 160000));
        employees.put("1597", new Employee(1597, "Ivan", "Vladimirovich", "Tugov", 4, 115000));
        employees.put("3286", new Employee(3286, "Nikolay", "Andreevich", "Teplov", 4, 110000));
    }

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
}
