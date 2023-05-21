package com.example.homework20;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private DepartmentService out;
    private static final Employee employee1 = new Employee(1423, "Ivan", "Petrovich", "Sidorov", 3, 110000);
    private static final Employee employee2 = new Employee(2521, "Petr", "Ivanovich", "Petrov", 1, 100000);
    private static final Employee employee3 = new Employee(8452, "Ignat", "Andreevich", "Ivanov", 3, 120000);
    private static final Employee employee4 = new Employee(1923, "Pavel", "Petrovich", "Krutov", 2, 160000);
    private static final Employee employee5 = new Employee(1597, "Ivan", "Vladimirovich", "Tugov", 4, 115000);
    private static final Employee employee6 = new Employee(3286, "Nikolay", "Andreevich", "Teplov", 4, 110000);

    private static final Collection<Employee> employees = List.of(employee1, employee2, employee3, employee4, employee5, employee6);


    @BeforeEach
    void setUp() {
        Mockito.when(employeeService.getAll()).thenReturn(employees);
    }

    public static Stream<Arguments> provideParamsForGetListDepartmentEmployeesTest() {
        return Stream.of(
                Arguments.of(employee1.getDepartmentId(), List.of(employee1, employee3)),
                Arguments.of(employee4.getDepartmentId(), List.of(employee4)),
                Arguments.of(5, List.of()));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForGetListDepartmentEmployeesTest")
    void getListDepartmentEmployeesTest(int departmentId, List<Employee> employeesExpected) {
        assertEquals(employeesExpected, out.getListDepartmentEmployees(departmentId));
    }

    public static Stream<Arguments> provideParamsForGetEmployeeWithMaxSalaryInDepartmentTest() {
        return Stream.of(
                Arguments.of(employee3.getDepartmentId(), employee3),
                Arguments.of(employee2.getDepartmentId(), employee2),
                Arguments.of(employee5.getDepartmentId(), employee5));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForGetEmployeeWithMaxSalaryInDepartmentTest")
    void getEmployeeWithMaxSalaryInDepartmentTest(int departmentId, Employee employeeExpected) {
        assertEquals(employeeExpected, out.getEmployeeWithMaxSalaryInDepartment(departmentId));
    }

    @Test
    void shouldThrowEmployeeNotFoundException() {
        assertThrows(EmployeeNotFoundException.class, () -> out.getEmployeeWithMaxSalaryInDepartment(5));
        assertThrows(EmployeeNotFoundException.class, () -> out.getEmployeeWithMinSalaryInDepartment(5));
    }
    public static Stream<Arguments> provideParamsForGetEmployeeWithMinSalaryInDepartmentTest() {
        return Stream.of(
                Arguments.of(employee1.getDepartmentId(), employee1),
                Arguments.of(employee2.getDepartmentId(), employee2),
                Arguments.of(employee6.getDepartmentId(), employee6));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForGetEmployeeWithMinSalaryInDepartmentTest")
    void getEmployeeWithMinSalaryInDepartmentTest(int departmentId, Employee employeeExpected) {
        assertEquals(employeeExpected, out.getEmployeeWithMinSalaryInDepartment(departmentId));
    }

    public static Stream<Arguments> provideParamsForGetSumDepartmentSalaryTest() {
        return Stream.of(
                Arguments.of(employee1.getDepartmentId(), 230000),
                Arguments.of(employee2.getDepartmentId(), 100000),
                Arguments.of(employee6.getDepartmentId(), 225000));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForGetSumDepartmentSalaryTest")
    void getSumDepartmentSalaryTest(int departmentId, int expectedSum) {
        assertEquals(expectedSum, out.getSumDepartmentSalary(departmentId));
    }

    @Test
    void getMapEmployeesSortedByDepartmentsTest() {
        Map<Integer, List<Employee>> employeesMapExpected = Map.of(
                1, List.of(employee2),
                2, List.of(employee4),
                3, List.of(employee1, employee3),
                4, List.of(employee5, employee6),
                5, List.of());
        assertEquals(employeesMapExpected, out.getMapEmployeesSortedByDepartments());
    }
}