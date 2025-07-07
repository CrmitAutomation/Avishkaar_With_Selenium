package utils;

import java.io.File;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

public class EmailUtils {

    public static void sendTestReport() {
        final String senderEmail = "lakshmanan.harikrishnan@crmit.com";
        final String appPassword = "azpvwfgildjfgyhg";  // Your Gmail App Password
        final String recipientEmail = "sanjay.kumar@crmit.com";
        final String ccEmail = "lakshmanan.harikrishnan@crmit.com"; // CC email address

        // Get report directory
        String reportDirPath = System.getProperty("user.dir") + "/reports";
        File reportDir = new File(reportDirPath);

        // Filter only files that start with "ExtentReport_" and end with ".html"
        File[] reportFiles = reportDir.listFiles((dir, name) ->
            name.toLowerCase().startsWith("extentreport_") && name.toLowerCase().endsWith(".html")
        );

        if (reportFiles == null || reportFiles.length == 0) {
            System.err.println("No ExtentReport HTML files found in: " + reportDirPath);
            return;
        }

        // Find the latest ExtentReport_*.html file
        File latestReport = reportFiles[0];
        for (File file : reportFiles) {
            if (file.lastModified() > latestReport.lastModified()) {
                latestReport = file;
            }
        }

        String reportPath = latestReport.getAbsolutePath();
        System.out.println("Latest ExtentReport selected: " + latestReport.getName());

        // SMTP settings
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.port", "587");

        // Session with authentication
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, appPassword);
            }
        });

        session.setDebug(true); // optional for debug logs

        try {
            // Create email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(ccEmail)); // Add CC
            message.setSubject("Test Execution Report - Automation COE");

            // Email body
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText("Hello,\n\nPlease find the latest automation test report attached for Avishkaar timesheet scenario execution.\n\nRegards,\nQA Team");

            // Attachment
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(latestReport);

            // Combine parts
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            // Send email
            Transport.send(message);
            System.out.println("Email sent successfully with report: " + latestReport.getName());

        } catch (Exception e) {
            System.err.println("Failed to send email");
            e.printStackTrace();
        }
    }
}
