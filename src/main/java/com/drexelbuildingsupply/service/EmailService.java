package com.drexelbuildingsupply.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    public void sendVerificationEmail(String toEmail, String username, String verificationCode) {
        String from = "your-email@gmail.com"; // Replace with your sender email
        String password = "your-app-password"; // Replace with your email/app password
        String subject = "Verify Your Drexel Account";
        String body = "Hi " + username + ",\n\n" +
                      "Thank you for registering with Drexel Communications. Please click the link below to verify your account:\n" +
                      "http://localhost:8080/verify?code=" + verificationCode + "\n\n" +
                      "Best regards,\nThe Drexel Team";

        // Mock email functionality
        if (isMockModeEnabled()) {
            logEmailDetails(toEmail, subject, body);
            return; // Skip actual email sending
        }

        // Email server configuration
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // Replace with your SMTP host
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Authenticate the email session
        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password); // Use sender email and app password
            }
        });

        try {
            // Construct the email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);
            System.out.println("Verification email sent to: " + toEmail);
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Determine if mock mode is enabled
    private boolean isMockModeEnabled() {
        return true; // Set to true to enable mock mode, false for real email sending
    }

    // Log email details to the console
    private void logEmailDetails(String toEmail, String subject, String body) {
        System.out.println("Mock Email Sent:");
        System.out.println("To: " + toEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }
}
