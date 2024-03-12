package swiss.re.app.helper;

import java.util.ArrayDeque;
import java.util.Queue;

import swiss.re.app.model.Employee;

public class EmployeeHelper {

    public static String printEmployeeId(Employee root) {
        StringBuilder builder = new StringBuilder();
        Queue<Employee> queue = new ArrayDeque<>();

        queue.add(root);

        while (!queue.isEmpty()) {
            Employee currentNode = queue.remove();

            if(builder.length() > 0) builder.append("-");
            builder.append(currentNode.getId());

            queue.addAll(currentNode.getEmployees());
        }
        return builder.toString();
    }

}
