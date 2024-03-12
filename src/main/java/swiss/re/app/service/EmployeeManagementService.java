package swiss.re.app.service;

import java.util.List;

import swiss.re.app.model.Employee;

public interface EmployeeManagementService {

    public List<Employee> findManagersEarnLessThanTheyShould(Employee rootEmployee);

    public List<Employee> findManagersEarnMoreThanTheyShould(Employee rootEmployee);

    public List<Employee> findEmployeesHaveLongReporingLine(Employee rootEmployee);

    public double calculateMinSalaryForManagerDiff(Employee employee);

    public double calculateMaxSalaryForManagerDiff(Employee employee);

}