package swiss.re.app.service;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.Function;

import swiss.re.app.model.Employee;

/**
 * 
 * Company would like to analyze its organizational structure and identify
 * potential improvements.
 * 
 * Board wants to make sure that every manager earns at least 20% more than
 * the average salary of its direct subordinates, but no more than 50% more than
 * that average.
 * 
 * Company wants to avoid too long reporting lines, therefore we would like to
 * identify all employees which have more than 4 managers between them and the
 * CEO.
 * 
 * Each line represents an employee (CEO included). CEO has no manager
 * specified. Number of rows can be up to 1000.
 * 
 * Write a simple program which will read the file and report:
 * - which managers earn less than they should, and by how much
 * - which managers earn more than they should, and by how much
 * - which employees have a reporting line which is too long, and by how much
 *
 */
public class EmployeeManagementServiceImpl implements EmployeeManagementService {
    private final int LEVEL_THRESHOLD = 4;
    private final double MANAGER_SALARY_MIN = 1.2; // manager should earns at least 20% more
    private final double MANAGER_SALARY_MAX = 1.5; // managers shouldn't earn more

    public double calculateMaxSalaryForManagerDiff(Employee employee){
        double average = employee.getAverage();
        double salary = employee.getSalary().doubleValue();

        return salary - average * MANAGER_SALARY_MAX;
    }

    public double calculateMinSalaryForManagerDiff(Employee employee){
        double average = employee.getAverage();
        double salary = employee.getSalary().doubleValue();
        
        return average * MANAGER_SALARY_MIN - salary;
    }

    public List<Employee> findManagersEarnLessThanTheyShould(Employee rootEmployee) {
        return findEmployees(rootEmployee,
                e -> e.getAverage() * MANAGER_SALARY_MAX < e.getSalary());
    }

    public List<Employee> findManagersEarnMoreThanTheyShould(Employee rootEmployee) {
        return findEmployees(rootEmployee,
                e -> e.getAverage() * MANAGER_SALARY_MIN > e.getSalary() );
    }

    public List<Employee> findEmployeesHaveLongReporingLine(Employee rootEmployee) {
        List<Employee> result = new ArrayList<>();
        countEmployeeReportLine(rootEmployee, 0, result);
        return result;
    }

    private List<Employee> findEmployees(Employee rootEmployee, Function<Employee, Boolean> function) {
        // todo are there ways to optimize this approach?
        List<Employee> result = new ArrayList<>();
        Queue<Employee> queue = new ArrayDeque<>();

        queue.add(rootEmployee);

        while (!queue.isEmpty()) {
            Employee currentEmployee = queue.remove();

            if (currentEmployee.isManager()) {
                // todo any optimization ??
                double average = countEmployeeSalaryAverage(currentEmployee);
                currentEmployee.setAverage(average);

                if (function.apply(currentEmployee)) {
                    result.add(currentEmployee);
                }

                queue.addAll(currentEmployee.getEmployees());
            }
        }

        return result;
    }

    private void countEmployeeReportLine(Employee employee, int level, List<Employee> list) {
        // DFS
        if (!employee.isManager()) {
            employee.setLevel(level);
            if (employee.getLevel() >= LEVEL_THRESHOLD) {
                list.add(employee);
            }

            return;
        }

        employee.setLevel(level);

        if (employee.getLevel() >= LEVEL_THRESHOLD) {
            list.add(employee);
        }

        for (Employee e : employee.getEmployees()) {
            countEmployeeReportLine(e, ++level, list);
        }
    }

    private Double countEmployeeSalaryAverage(Employee employee) {
        // BFS
        Queue<Employee> queue = new ArrayDeque<>();

        queue.addAll(employee.getEmployees());

        double count = 0;
        int total = 0;

        while (!queue.isEmpty()) {
            Employee currentEmployee = queue.remove();
            count++;

            total += currentEmployee.getSalary().intValue();
            queue.addAll(currentEmployee.getEmployees());
        }

        return total / count;
    }

}