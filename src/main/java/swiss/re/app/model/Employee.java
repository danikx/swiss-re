package swiss.re.app.model;

import java.util.LinkedList;
import java.util.List;


public class Employee {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer salary;
    private List<Employee> employees = new LinkedList<>();
    private double average;
    private int level = -1;


    public void setLevel(int level){
        this.level = level;
    }

    public int getLevel(){
        return this.level;
    }

    public void setAverage(double average){
        this.average = average;
    }

    public double getAverage(){
        return this.average;
    }

    public boolean isManager() {
        return employees.size() > 0;
    }

    public void addEmployee(Employee employee) {
        this.employees.add(employee);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    };

    public String getFirstName() {
        return this.firstName;
    };

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Integer getSalary() {
        return this.salary;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return this.employees;
    }
}
