package swiss.re.app;

import java.io.File;
import java.util.List;

import swiss.re.app.helper.Loader;
import swiss.re.app.model.Employee;
import swiss.re.app.service.EmployeeManagementService;
import swiss.re.app.service.EmployeeManagementServiceImpl;

public class App {
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Please provide absolute file path.");
            return;
        }

        String fileName = args[0];

        try {
            Employee employee = Loader.loadEmployeeHierarchy(new File(fileName));

            EmployeeManagementService service = new EmployeeManagementServiceImpl();

            List<Employee> managersEarnLess = service.findManagersEarnLessThanTheyShould(employee);
            List<Employee> managersEarnMore = service.findManagersEarnMoreThanTheyShould(employee);
            List<Employee> employeesLongReportLine = service.findEmployeesHaveLongReporingLine(employee);

            System.out.println();
            if(managersEarnLess.isEmpty()){
                System.out.println("\nThere are no managers who earn less.");
            } else {

                System.out.println("\nManagers earn less: ");
                for (Employee e : managersEarnLess) {
                    System.out.println(
                            e.getId() + " " + e.getFirstName() + " diff: " + service.calculateMinSalaryForManagerDiff(e));
                }
            }

            if(managersEarnMore.isEmpty()){
                System.out.println("\nThere are no managers whoe earn more.");
            } else {

                System.out.println("\nManagers earn more: ");
    
                for (Employee e : managersEarnMore) {
                    System.out.println("  - "+
                            e.getId() + " " + e.getFirstName() + " diff: " + service.calculateMaxSalaryForManagerDiff(e));
                }
            }

            if(employeesLongReportLine.isEmpty()){
                System.out.println("\nThere are not employees with long report line.");
            } else {

                System.out.println();
                System.out.println("Employees with long report line: ");
                for (Employee e : employeesLongReportLine) {
                    System.out.println("  - " + e.getId() + " " + e.getFirstName());
                }
            }

        } catch (Exception e) {
            System.out.println("File not found: " + fileName);
        }
    }
}
