import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface EmployeeDao {
    List<Employee> getAll() throws EmployeeDaoException;

    public class EmployeeDaoException extends Exception {
        public EmployeeDaoException(String message){}
    }
}
