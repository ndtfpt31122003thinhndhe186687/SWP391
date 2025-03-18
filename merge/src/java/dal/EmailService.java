package dal;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.text.NumberFormat;
import java.util.Locale;

public class EmailService {

    private static final String USERNAME = "ducthinh20032003@gmail.com";
    private static final String PASSWORD = "fjuk kgua lvis rzkq";
    private static final int THREAD_POOL_SIZE = 10; // Số luồng gửi email song song
    private static final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static void sendReminder(String recipientEmail, double amount, String dueDate) {
        executorService.submit(() -> {
            try {
                NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
                String formattedAmount = formatter.format(amount);
                String host = "smtp.gmail.com";

                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", host);
                properties.put("mail.smtp.port", "587");

                Session session = Session.getInstance(properties, new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(USERNAME));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject(MimeUtility.encodeText("Thông báo đến hạn thanh toán khoản vay", "UTF-8", "B"));

                String htmlContent = "<p>Kính gửi Quý khách,</p>"
                        + "<p>Khoản vay của bạn cần thanh toán số tiền <b>" + formattedAmount + " VND</b> vào ngày <b>" + dueDate + "</b>.</p>"
                        + "<p>Vui lòng thanh toán đúng hạn để tránh phí phạt.</p>"
                        + "<p>Trân trọng,<br>Ngân hàng FinBank</p>";

                MimeBodyPart bodyPart = new MimeBodyPart();
                bodyPart.setContent(htmlContent, "text/html; charset=UTF-8");

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(bodyPart);

                message.setContent(multipart);

                Transport.send(message);
                System.out.println("Email sent to " + recipientEmail);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(EmailService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public static void shutdown() {
        executorService.shutdown();
    }
}
