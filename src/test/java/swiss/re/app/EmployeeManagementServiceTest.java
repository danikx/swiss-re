package swiss.re.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

import swiss.re.app.helper.Loader;
import swiss.re.app.model.Employee;
import swiss.re.app.service.EmployeeManagementService;
import swiss.re.app.service.EmployeeManagementServiceImpl;

public class EmployeeManagementServiceTest {

    @Test
    public void shouldFindManagersShouldEarnLess() throws IOException {
        EmployeeManagementService service = new EmployeeManagementServiceImpl();
        List<Employee> managersShouldEarnLess = service
                .findManagersEarnLessThanTheyShould(Loader.loadEmployeeHierarchy("manager-should-earn-less.csv"));

        Map<Integer, Employee> map = managersShouldEarnLess.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));

        assertEquals(2, managersShouldEarnLess.size());
        //
        assertNotNull(map.get(123));
        assertNotNull(map.get(300));
        assertEquals(34200.0, map.get(123).getAverage(), 1);
        assertEquals(32000.0, map.get(300).getAverage(), 1);

        // by how much
        assertEquals(108700.0, service.calculateMaxSalaryForManagerDiff(map.get(123)), 0);
        assertEquals(2000.0, service.calculateMaxSalaryForManagerDiff(map.get(300)), 0);
    }

    @Test
    public void shouldFindManagersShouldEarnMore() throws IOException {
        EmployeeManagementService service = new EmployeeManagementServiceImpl();
        List<Employee> managersShouldEarnMore = service
                .findManagersEarnMoreThanTheyShould(Loader.loadEmployeeHierarchy("manager-should-earn-more.csv"));

        Map<Integer, Employee> map = managersShouldEarnMore.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));

        assertEquals(2, managersShouldEarnMore.size());
        assertNotNull(map.get(124));
        assertNotNull(map.get(305));

        assertEquals(35600.0, service.calculateMinSalaryForManagerDiff(map.get(124)), 0);
        assertEquals(2000.0, service.calculateMinSalaryForManagerDiff(map.get(305)), 0);
    }

    @Test
    public void shouldFindManagersLongReportingLine() throws IOException {
        EmployeeManagementService service = new EmployeeManagementServiceImpl();
        List<Employee> managersLongReportingLine = service
                .findEmployeesHaveLongReporingLine(Loader.loadEmployeeHierarchy("long-report-line.csv"));

        assertEquals(1, managersLongReportingLine.size());
        assertEquals("Harley", managersLongReportingLine.get(0).getFirstName());
    }

}
