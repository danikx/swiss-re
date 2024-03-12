package swiss.re.app.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import swiss.re.app.formatter.EmployeeFormatter;
import swiss.re.app.model.Employee;

public class Loader {

    public static Employee loadEmployeeHierarchy(String fileName) throws IOException {
        return EmployeeFormatter.parse(loadCsvFile(true, 
                Loader.class.getClassLoader().getResourceAsStream(fileName)));
    }

    public static Employee loadEmployeeHierarchy(File fileName) throws IOException {
        return EmployeeFormatter.parse(loadCsvFile(true, new FileInputStream(fileName)));
    }

    public static List<String> loadCsvFile(boolean skipHeader, InputStream stream) throws IOException {
        try (BufferedReader fis = new BufferedReader( new InputStreamReader(stream));) {
            return fis.lines()
                    .skip(skipHeader ? 1 : 0)
                    .collect(Collectors.toList());
        }
    }
}
