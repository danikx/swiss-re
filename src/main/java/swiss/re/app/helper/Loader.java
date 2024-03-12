package swiss.re.app.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import swiss.re.app.formatter.EmployeeFormatter;
import swiss.re.app.model.Employee;

public class Loader {

    public static Employee loadEmployeeHierarchy(String fileName) throws IOException {
        return EmployeeFormatter.parse(loadCsvFile(fileName, true));
    }

    public static List<String> loadCsvFile(String filePath, boolean skipHeader) throws IOException {
        try (BufferedReader fis = new BufferedReader(
                new InputStreamReader(Loader.class.getClassLoader().getResourceAsStream(filePath)));) {
            return fis.lines()
                    .skip(skipHeader ? 1 : 0)
                    .collect(Collectors.toList());
        }
    }
}
