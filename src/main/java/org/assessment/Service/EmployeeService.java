package org.assessment.Service;

import org.assessment.Model.Employee;

import java.util.Map;

public class EmployeeService {

    private Employee ceo;
    public void buildHierarchy(Map<Integer, Employee> employees) {
        for(Employee employee:employees.values()){
            if(employee.getManagerId()==null){
                ceo= employee;
            }else{
                Employee manager = employees.get(employee.getManagerId());
                manager.addReportee(employee);
            }
        }
    }

    public Employee getCEO(){
        return ceo;
    }

    public void findUnderpaid(Employee manager) {
        if (manager.getReportees().isEmpty()) return;

        double avg = getAverageSalary(manager);

        double minAllowed = avg * 1.20;
        minAllowed = Math.round(minAllowed * 100.0) / 100.0;
        if (manager.getSalary() < minAllowed) {
            System.out.println(manager.getFirstName() +" "+ manager.getLastName()+
                    " managers earn less than they should "+ " by " +
                    (minAllowed-manager.getSalary()) + "."
            );
        }

        for (Employee employee : manager.getReportees()) {
            findUnderpaid(employee);
        }
    }

    public void findOverpaid(Employee manager) {
        if (manager.getReportees().isEmpty()) return;
        double avg = getAverageSalary(manager);
        double maxAllowed = avg * 1.50;
        maxAllowed = Math.round(maxAllowed*100.0)/100.0;
        if (manager.getSalary() > maxAllowed) {
            System.out.println(manager.getFirstName() +" "+ manager.getLastName()+
                    " managers earn more than they should"+ " by " +
                    (manager.getSalary() - maxAllowed) + "."
            );
        }

        for (Employee e : manager.getReportees()) {
            findOverpaid(e);
        }
    }

    private double getAverageSalary(Employee manager){
        return manager.getReportees()
                .stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0);
    }

    public void checkReportingLine(Employee employee, int depth,int allowedDepth) {

        if (depth > allowedDepth) {
            System.out.println(
                    "Employee: " + employee.getFirstName() +" "+ employee.getLastName()+
                            ", have a reporting line which is too long" +
                            " by " + (depth - allowedDepth) + "."
            );
        }

        for (Employee child : employee.getReportees()) {
            checkReportingLine(child, depth + 1,allowedDepth);
        }
    }

}
