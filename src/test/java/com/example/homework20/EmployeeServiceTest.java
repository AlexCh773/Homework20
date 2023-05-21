package com.example.homework20;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeServiceTest {
    public final EmployeeService out = new EmployeeService();
    public static final Employee employee1 = new Employee(1423, "Ivan", "Petrovich", "Sidorov", 3, 110000);
    public static final Employee employee2 = new Employee(2521, "Petr", "Ivanovich", "Petrov", 1, 100000);

    @AfterEach
    public void clear() {
        EmployeeService.employees.clear();
    }

    public static Stream<Arguments> provideParamsForAddEmployee() {
        Map<String, Employee> employeesExpect1 = new HashMap<>();
        employeesExpect1.put(Integer.toString(employee1.getPassportNumber()), employee1);
        List<Employee> employees1 = List.of();
        Map<String, Employee> employeesExpect2 = new HashMap<>();
        employeesExpect2.put(Integer.toString(employee1.getPassportNumber()), employee1);
        employeesExpect2.put(Integer.toString(employee2.getPassportNumber()), employee2);
        List<Employee> employees2 = List.of(employee1);
        return Stream.of(
                Arguments.of(employeesExpect1, employees1, employee1.getPassportNumber(), employee1.getFirstName(),
                        employee1.getMiddleName(), employee1.getLastName(), employee1.getDepartmentId(), employee1.getSalary(), 2),
                Arguments.of(employeesExpect2, employees2, employee2.getPassportNumber(), employee2.getFirstName(),
                        employee2.getMiddleName(), employee2.getLastName(), employee2.getDepartmentId(), employee2.getSalary(), 3));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForAddEmployee")
    public void addEmployee(Map<String, Employee> employeesExpected, List<Employee> employees, int passportNumber, String firstName, String middleName,
                            String lastName, int departmentId, int salary, int nextIdExpected) {
        for (Employee employee : employees) {
            EmployeeService.employees.put(Integer.toString(employee.getPassportNumber()), employee);
        }
        Employee employeeActual = out.addEmployee(passportNumber, firstName, middleName, lastName, departmentId, salary);
        assertEquals(employeesExpected, EmployeeService.employees);
        assertEquals(employeesExpected.get(Integer.toString(passportNumber)), employeeActual);
        assertEquals(nextIdExpected, Employee.nextId);
    }

    @Test
    public void shouldThrowEmployeeAlreadyAddedException() {
        EmployeeService.employees.put(Integer.toString(employee1.getPassportNumber()), employee1);
        assertThrows(EmployeeAlreadyAddedException.class, () -> out.addEmployee(employee1.getPassportNumber(), employee1.getFirstName(),
                employee1.getMiddleName(), employee1.getLastName(), employee1.getDepartmentId(), employee1.getSalary()));
    }

    public static Stream<Arguments> provideParamsForRemoveEmployee() {
        Map<String, Employee> employeesExpected1 = new HashMap<>();
        List<Employee> employees1 = List.of(employee1);
        Map<String, Employee> employeesExpected2 = new HashMap<>();
        employeesExpected2.put(Integer.toString(employee1.getPassportNumber()), employee1);
        List<Employee> employees2 = List.of(employee1, employee2);
        return Stream.of(
                Arguments.of(employeesExpected1, employees1, employee1.getPassportNumber(), employee1),
                Arguments.of(employeesExpected2, employees2, employee2.getPassportNumber(), employee2));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForRemoveEmployee")
    public void removeEmployee(Map<String, Employee> employeesExpected, List<Employee> employees, int passportNumber, Employee employeeExpected) {
        for (Employee employee : employees) {
            EmployeeService.employees.put(Integer.toString(employee.getPassportNumber()), employee);
        }
        Employee employeeActual = out.removeEmployee(passportNumber);
        assertEquals(employeesExpected, EmployeeService.employees);
        assertEquals(employeeExpected, employeeActual);
    }

    public static Stream<Arguments> provideParamsForFindEmployee() {
        Map<String, Employee> employeesExpected1 = new HashMap<>();
        List<Employee> employees1 = List.of(employee1);
        employeesExpected1.put(Integer.toString(employee1.getPassportNumber()), employee1);
        Map<String, Employee> employeesExpected2 = new HashMap<>();
        employeesExpected2.put(Integer.toString(employee1.getPassportNumber()), employee1);
        employeesExpected2.put(Integer.toString(employee2.getPassportNumber()), employee2);
        List<Employee> employees2 = List.of(employee1, employee2);
        return Stream.of(
                Arguments.of(employeesExpected1, employees1, employee1.getPassportNumber(), employee1),
                Arguments.of(employeesExpected2, employees2, employee2.getPassportNumber(), employee2));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForFindEmployee")
    public void findEmployee(Map<String, Employee> employeesExpected, List<Employee> employees, int passportNumber, Employee employeeExpected) {
        for (Employee employee : employees) {
            EmployeeService.employees.put(Integer.toString(employee.getPassportNumber()), employee);
        }
        Employee employeeActual = out.findEmployeeByPassport(passportNumber);
        assertEquals(employeesExpected, EmployeeService.employees);
        assertEquals(employeeExpected, employeeActual);
    }

    @Test
    public void shouldThrowEmployeeNotFoundException() {
        assertThrows(EmployeeNotFoundException.class, () -> out.findEmployeeByPassport(employee1.getPassportNumber()));
    }

    @Test
    public void getAllTest() {
        Collection<Employee> collectionExpected = List.of(employee1, employee2);
        EmployeeService.employees.put(Integer.toString(employee1.getPassportNumber()), employee1);
        EmployeeService.employees.put(Integer.toString(employee2.getPassportNumber()), employee2);
        assertEquals(collectionExpected, new ArrayList<>(out.getAll()));
    }
}