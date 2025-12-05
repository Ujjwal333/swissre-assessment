package org.assessment.Util;

import org.assessment.Model.Employee;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CsvReader {
    public Map<Integer, Employee> readEmployees(InputStream inputStream){
        Map<Integer,Employee> employees = new HashMap<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String row = reader.readLine();
            while((row= reader.readLine())!=null){
                String[] employee = row.split(",");
                int id = Integer.parseInt(employee[0].trim());
                String firstName = employee[1].trim();
                String lastName = employee[2].trim();
                double salary = Double.parseDouble(employee[3].trim());
                Integer managerId = null;
                if (employee.length > 4 && employee[4] != null && !employee[4].trim().isEmpty()) {
                    managerId = Integer.parseInt(employee[4].trim());
                }
                Employee newEmployee= new Employee(id,firstName,lastName,salary,managerId);
                employees.put(id,newEmployee);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
}
