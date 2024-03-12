package swiss.re.app;

import java.io.IOException;
import java.util.List;

import swiss.re.app.helper.Loader;
import swiss.re.app.model.Employee;
import swiss.re.app.service.EmployeeManagementService;
import swiss.re.app.service.EmployeeManagementServiceImpl;

public class App {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Please provide file path.");
            return;
        }

        String fileName = args[0];

        try {
            Employee employee = Loader.loadEmployeeHierarchy(fileName);

            EmployeeManagementService service = new EmployeeManagementServiceImpl();

            List<Employee> managersEarnLess = service.findManagersEarnLessThanTheyShould(employee);
            List<Employee> managersEarnMore = service.findManagersEarnMoreThanTheyShould(employee);
            List<Employee> employeesLongReportLine = service.findEmployeesHaveLongReporingLine(employee);

            System.out.println("Managers earn less: ");
            for (Employee e : managersEarnLess) {
                System.out.println(
                        e.getId() + " " + e.getFirstName() + " diff: " + service.calculateMinSalaryForManagerDiff(e));
            }

            System.out.println();
            System.out.println("Managers earn more: ");

            for (Employee e : managersEarnMore) {
                System.out.println(
                        e.getId() + " " + e.getFirstName() + " diff: " + service.calculateMaxSalaryForManagerDiff(e));
            }

            System.out.println();
            System.out.println("Employees with long report line: ");
            for (Employee e : employeesLongReportLine) {
                System.out.println("  - " + e.getId() + " " + e.getFirstName());
            }

        } catch (IOException e) {
            System.out.println("File not found: " + fileName);
        }
    }
}
