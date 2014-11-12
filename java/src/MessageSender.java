import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

// TODO: remove MessagingException etc
// TODO: primitive obsession
public interface MessageSender {
    void send(String sender, String subject, String body, String recipient) throws AddressException, MessagingException;
}
