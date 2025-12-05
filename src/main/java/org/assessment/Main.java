package org.assessment;

import org.assessment.Model.Employee;
import org.assessment.Service.EmployeeService;
import org.assessment.Util.CsvReader;

import java.io.InputStream;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("employee_data.csv");

        if (inputStream == null) {
            throw new RuntimeException("File not found in resources folder");
        }
        CsvReader reader = new CsvReader();
        Map<Integer, Employee> employees = reader.readEmployees(inputStream);

        EmployeeService service = new EmployeeService();
        service.buildHierarchy(employees);
        Employee ceo = service.getCEO();
        System.out.println("Underpaid Managers: ");
        service.findUnderpaid(ceo);
        System.out.println("-------------------------");
        System.out.println("Overpaid Managers: ");
        service.findOverpaid(ceo);
        System.out.println("-------------------------");
        int allowedDepth =4;
        service.checkReportingLine(ceo,0,allowedDepth);
    }
}