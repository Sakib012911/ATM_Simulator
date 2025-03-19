package atm.machine;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Email {
   public void OtpEmail(int otp, String email){
        final String senderEmail = ""; // Your email
        final String senderPassword = ""; // Your email password or app password
        final String recipientEmail = email; // Receiver's email.

        // Configure mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587"); // Use 465 for SSL

        // Create session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Star Bank Alert");
            message.setText("Star Bank \n\n"+"This OTP vaild only for 2 Minutes\n"+otp+"\n"+"Thank You For choosing Star Bank");

            // Send email
            Transport.send(message);
            System.out.println("OTP sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void WithdrawnEmail(int amount,String email) {
        final String senderEmail = ""; // Your email
        final String senderPassword = ""; // Your email password or app password
        final String recipientEmail = email; // Receiver's email.

        // Configure mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587"); // Use 465 for SSL

        // Create session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Star Bank Alert");
            message.setText("Thanks For choosing Star Bank ATM Facility\n" +  amount+" Rs"+" Withdrawn\n"+"If its not You kindly contact report@starbank.in");

            // Send email
            Transport.send(message);
            System.out.println("Withdrawn Message sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
