import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFileDao implements EmployeeDao {
    private String fileName;

    public EmployeeFileDao(String fileName) {
        this.fileName = fileName;
    }

    // TODO deal with exceptions
    @Override
    public List<Employee> getAll() throws IOException, ParseException {
        List<Employee> employeeList = new ArrayList<Employee>();
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String str = "";
        str = in.readLine(); // skip header
        while ((str = in.readLine()) != null) {
            String[] employeeData = str.split(", ");
            Employee employee = new Employee(employeeData[1], employeeData[0], employeeData[2], employeeData[3]);
            employeeList.add(employee);
        }

        return employeeList;
    }
}
