import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AcceptanceTest {

	private List<HashMap> messagesSent;
	private BirthdayService service;
	
	@Before
	public void setUp() throws Exception {
		messagesSent = new ArrayList<HashMap>();

        MessageSender messageSender = new TestMessageAdaptor();
        EmployeeDao employeeDao = new TestEmployeeDao();
		service = new BirthdayService(messageSender, employeeDao);
	}

	@Test
	public void sendsMessageForBirthdays() throws Exception {
		service.sendGreetings(new OurDate("2008/10/08"));
		
		assertEquals("message not sent?", 1, messagesSent.size());
		HashMap message = messagesSent.get(0);
		assertEquals("Happy Birthday, dear John!", message.get("body"));
		assertEquals("Happy Birthday!", message.get("subject"));
		assertEquals("john.doe@foobar.com", message.get("recipient"));
	}
	
	@Test
	public void willNotSendEmailsWhenNobodysBirthday() throws Exception {		
		service.sendGreetings(new OurDate("2008/01/01"));
		
		assertEquals("what? messages?", 0, messagesSent.size());
	}

    private class TestMessageAdaptor implements MessageSender {
        @Override
        public void send(String sender, String subject, String body, String recipient) throws AddressException, MessagingException {
            HashMap msg = new HashMap();
            msg.put("sender", sender);
            msg.put("subject", subject);
            msg.put("recipient", recipient);
            msg.put("body", body);

            messagesSent.add(msg);
        }
    }

    private class TestEmployeeDao implements EmployeeDao {
        @Override
        public List<Employee> getAll() throws IOException, ParseException {
            List<Employee> employeeList = new ArrayList<Employee>();

            employeeList.add(new Employee("John", "Doe", "1982/10/08", "john.doe@foobar.com"));
            employeeList.add(new Employee("Mary", "Ann", "1975/03/11", "mary.ann@foobar.com"));

            return employeeList;
        }
    }
}
