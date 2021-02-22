package by.jwd.task6.util;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    private static ResourceBundle bundle = ResourceBundle.getBundle("mail/mail");

    public static void send(String sendToEmail, String mailSubject, String mailText) throws MessagingException {

        Properties properties = readProperties();
        Session mailSession = SessionFactory.createSession(properties);
        MimeMessage message = provideMessage(mailSession, mailSubject, mailText, sendToEmail);
        
        Transport transport = mailSession.getTransport();            // TODO При вызове статического send стучится в 25 порт, разобраться
        transport.connect();
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
    
    private static MimeMessage provideMessage(Session mailSession, String mailSubject, String mailText, String sendToEmail) throws MessagingException {
        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject(mailSubject);
        message.setContent(mailText, "text/html;charset=utf-8");
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
        return message;
     }
     
    private static Properties readProperties() {
        Properties properties = new Properties();
        Enumeration<String> keys = bundle.getKeys();
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                properties.put(key, bundle.getString(key));
            }
        return properties;
    }
}
