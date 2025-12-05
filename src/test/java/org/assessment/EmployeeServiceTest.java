package org.assessment;

import org.assessment.Model.Employee;
import org.assessment.Service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService service;
    private Map<Integer, Employee> employees;

    @BeforeEach
    void setUp() {
        service = new EmployeeService();
        employees = new HashMap<>();

        employees.put(1, new Employee(1, "Eleanor", "Sanders", 120000, null));
        employees.put(2, new Employee(2, "Daniel", "Hawthorne", 75000, 1));
        employees.put(3, new Employee(3, "Priya", "Sharma", 72000, 1));
        employees.put(4, new Employee(4, "Liam", "Carter", 58000, 2));
        employees.put(5, new Employee(5, "Sophia", "Martinez", 62000, 2));
        employees.put(6, new Employee(6, "Noah", "Bennett", 45000, 4));
        employees.put(7, new Employee(7, "Maya", "Fernandez", 38000, 6));
    }

    @Test
    void testBuildHierarchyAndCEO() {
        service.buildHierarchy(employees);

        Employee ceo = service.getCEO();
        assertNotNull(ceo);
        assertEquals(1, ceo.getId());
        assertEquals(2, ceo.getReportees().size());
    }

    @Test
    void testFindUnderpaid() {
        service.buildHierarchy(employees);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        service.findUnderpaid(service.getCEO());

        String output = out.toString();
        assertTrue(output.contains("Noah Bennett"));
        assertTrue(output.contains("less than"));
    }

    @Test
    void testFindOverpaid() {
        employees.put(2, new Employee(2, "Daniel", "Hawthorne", 150000, 1));

        service.buildHierarchy(employees);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        service.findOverpaid(service.getCEO());

        String output = out.toString();
        assertTrue(output.contains("Daniel Hawthorne"));
        assertTrue(output.contains("more than"));
    }

    @Test
    void testCheckReportingLine() {
        service.buildHierarchy(employees);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        service.checkReportingLine(service.getCEO(), 1, 4);

        String output = out.toString();
        assertTrue(output.contains("too long by"));
        assertTrue(output.contains("Maya Fernandez"));
    }
}
