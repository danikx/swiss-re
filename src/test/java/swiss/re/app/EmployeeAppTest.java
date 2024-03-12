package swiss.re.app;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import swiss.re.app.exceptions.EmployeeFormatException;
import swiss.re.app.formatter.EmployeeFormatter;
import swiss.re.app.helper.EmployeeHelper;
import swiss.re.app.helper.Loader;
import swiss.re.app.model.Employee;
import swiss.re.app.service.EmployeeManagementServiceImpl;

public class EmployeeAppTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void shouldLoadEmployeesHierarchy() throws IOException {
        Employee employee = Loader.loadEmployeeHierarchy("data.csv");

        assertEquals("123-124-125-300-305", EmployeeHelper.printEmployeeId(employee));
    }

    @Test
    public void shouldFindManagersShouldEarnLess() throws IOException {
        EmployeeManagementServiceImpl service = new EmployeeManagementServiceImpl();
        List<Employee> managersShouldEarnLess = service
                .findManagersEarnLessThanTheyShould(Loader.loadEmployeeHierarchy("data.csv"));

        Map<Integer, Employee> map = managersShouldEarnLess.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));

        assertEquals(3, managersShouldEarnLess.size());
        // by how much
        assertEquals(44000.0, map.get(123).getAverage(), 1);
        assertEquals(42000.0, map.get(124).getAverage(), 1);
        assertEquals(34000.0, map.get(300).getAverage(), 1);

        assertEquals(16000.0, map.get(123).getSalaryVsAverageDiff(), 1);
        assertEquals(3000.0, map.get(124).getSalaryVsAverageDiff(), 1);
        assertEquals(16000.0, map.get(300).getSalaryVsAverageDiff(), 1);
    }

    @Test
    public void shouldFindManagersShouldEarnMore() throws IOException {
        EmployeeManagementServiceImpl service = new EmployeeManagementServiceImpl();
        List<Employee> managersShouldEarnMore = service
                .findManagersEarnMoreThanTheyShould(Loader.loadEmployeeHierarchy("manager-should-earn-more.csv"));

        assertEquals(1, managersShouldEarnMore.size());
        assertEquals("Martin", managersShouldEarnMore.get(0).getFirstName());
        assertEquals(28000.0, managersShouldEarnMore.get(0).getSalaryVsAverageDiff(), 1);
    }

    @Test
    public void shouldFindManagersLongReportingLine() throws IOException {
        EmployeeManagementServiceImpl service = new EmployeeManagementServiceImpl();
        List<Employee> managersLongReportingLine = service.findEmployeesHaveLongReporingLine(Loader.loadEmployeeHierarchy("long-report-line.csv"));

        assertEquals(1, managersLongReportingLine.size());
        assertEquals("Harley", managersLongReportingLine.get(0).getFirstName());
    }

    @Test
    public void shouldThrowExceptionWrongEmployeeId() throws IOException {
        String wrongIdValue = "aa";
        exceptionRule.expect(EmployeeFormatException.class);
        exceptionRule.expectMessage(EmployeeFormatter.EMPLOYEE_ID_WRONG_MESSAGE + ": " + wrongIdValue);

        EmployeeFormatter.mapper(wrongIdValue + ",first,last");
    }

    @Test
    public void shouldThrowExceptionWrongEmployeeSalary() throws IOException {
        String wrongSalaryValue = "aa";
        exceptionRule.expect(EmployeeFormatException.class);
        exceptionRule.expectMessage(EmployeeFormatter.EMPLOYEE_SALARY_WRONG_MESSAGE + ": " + wrongSalaryValue);

        EmployeeFormatter.mapper("1,first,last," + wrongSalaryValue);
    }

    @Test
    public void shouldThrowExceptionWrongEmployeeManagerId() throws IOException {
        String wrongManagerIdValue = "aa";
        exceptionRule.expect(EmployeeFormatException.class);
        exceptionRule.expectMessage(EmployeeFormatter.EMPLOYEE_MANAGER_ID_WRONG_MESSAGE + ": " + wrongManagerIdValue);

        EmployeeFormatter.mapper("1,first,last,11," + wrongManagerIdValue);
    }
}
