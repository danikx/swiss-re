package swiss.re.app;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import swiss.re.app.exceptions.EmployeeFormatException;
import swiss.re.app.formatter.EmployeeFormatter;
import swiss.re.app.helper.EmployeeHelper;
import swiss.re.app.helper.Loader;
import swiss.re.app.model.Employee;

public class LoaderTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void shouldLoadEmployeesHierarchy() throws IOException {
        Employee employee = Loader.loadEmployeeHierarchy("data.csv");

        assertEquals("123-124-125-300-305", EmployeeHelper.printEmployeeId(employee));
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
