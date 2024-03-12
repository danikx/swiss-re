package swiss.re.app.formatter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import swiss.re.app.exceptions.EmployeeFormatException;
import swiss.re.app.model.Employee;

public class EmployeeFormatter {

    public static String EMPLOYEE_ID_WRONG_MESSAGE = "Employee id wrong format";
    public static String EMPLOYEE_SALARY_WRONG_MESSAGE = "Employee salary wrong format";
    public static String EMPLOYEE_MANAGER_ID_WRONG_MESSAGE = "Employee Manager id wrong format";
    private static Integer ceoId = null;
    private static Map<Integer, Employee> map = new HashMap<>();

    public static Employee parse(List<String> list) {
        list.stream()
                .map(EmployeeFormatter::mapper)
                .collect(Collectors.toList());

        Employee ceo = map.get(ceoId);
        ceoId = null;
        map.clear();
        return ceo;
    }

    public static Employee mapper(String line) {
        String[] values = line.split(",");
        Employee employee = new Employee();

        employee.setId(formatInteger(values[0], EMPLOYEE_ID_WRONG_MESSAGE));
        employee.setFirstName(values[1].trim());
        employee.setLastName(values[2].trim());
        employee.setSalary(formatInteger(values[3], EMPLOYEE_SALARY_WRONG_MESSAGE));
        Integer managerId = values.length == 5 ? formatInteger(values[4], EMPLOYEE_MANAGER_ID_WRONG_MESSAGE) : null;

        if (managerId == null) {
            ceoId = employee.getId();
            map.put(employee.getId(), employee); // special case for CEO

        } else {
            if (!map.containsKey(managerId)) {
                throw new RuntimeException(); // assimption that managerId should be applyed first
            }

            map.get(managerId).addEmployee(employee);
            map.put(employee.getId(), employee);
        }

        return employee;
    }

    private static Integer formatInteger(String value, String messageOnError) {
        try {
            return Integer.parseInt(value.trim());
        } catch (Exception e) {
            throw new EmployeeFormatException(messageOnError + ": " + value);
        }
    }
}
