package edu.najah.library.utils;

import edu.najah.library.models.services.UserDAOImp;
import edu.najah.library.models.User;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Properties;

public class EmailService {

    private static final String senderEmail = "yahya1dalbah@gmail.com";
    private static final String senderPassword = "idqn ikak dyok rxlo";     // app password (not my gmail password)

    public static void sendEmail(String recipientEmail, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP host
        props.put("mail.smtp.port", "587");           // SMTP port
        props.put("mail.smtp.auth", "true");          // Enable authentication
        props.put("mail.smtp.starttls.enable", "true"); // Enable STARTTLS

        // Session for authentication
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

    public static String generateResetToken(String email) {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[24];
        random.nextBytes(bytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);

        // save token and expiration to the database
        UserDAOImp userDAO = new UserDAOImp();
        User user = userDAO.getUserByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("Email not found");
        }

        user.setResetToken(token);
        user.setTokenExpiration(LocalDateTime.now().plusHours(1)); // token valid for 1 hour

        userDAO.updateUser(user);

        return token;
    }
}
