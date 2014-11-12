
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public class BirthdayService {
    private MessageSender messageSender;
    private EmployeeDao employeeDao;

    public BirthdayService(MessageSender messageSender, EmployeeDao employeeDao) {
        this.messageSender = messageSender;
        this.employeeDao = employeeDao;
    }

    public void sendGreetings(OurDate ourDate) throws IOException, ParseException, MessagingException {
        List<Employee> employees = employeeDao.getAll();
        for (Employee employee: employees) {
            if (employee.isBirthday(ourDate)) {
                String recipient = employee.getEmail();
                String body = "Happy Birthday, dear %NAME%!".replace("%NAME%", employee.getFirstName());
                String subject = "Happy Birthday!";
                messageSender.send("sender@here.com", subject, body, recipient);
            }
        }
	}


    public static void main(String[] args) {

        BirthdayService service = new BirthdayService(
                new EmailAdaptor( "localhost", 25),
                new EmployeeFileDao("employee_data.txt"));
        try {
            service.sendGreetings(new OurDate("2008/10/08"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
