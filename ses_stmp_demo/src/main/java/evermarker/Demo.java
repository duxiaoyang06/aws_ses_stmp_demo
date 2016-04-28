package evermarker;

import java.lang.Exception;
import java.lang.String;
import java.lang.System;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Demo {

    /*
    * 0.未移除沙盒之前发送和接受方必须是verified的
    * 1.QQ邮箱当发件箱时，显示发送成功但实际没有发送成功，模拟器也无法使用QQ邮箱正常发送邮件
    * 2.端口固定为25，不可更改
    * 3.发件host、SMTP用户名、密码可配置
    * */

    static final String FROM = PropUtil.get("FROM");   // 发件箱


    static final String TO = PropUtil.get("TO");  // 收件箱

    static final String BODY = "This email was sent through the Amazon SES SMTP interface by using Java.";
    static final String SUBJECT = "Amazon SES test (SMTP interface accessed using Java)";

    // Supply your SMTP credentials below. Note that your SMTP credentials are different from your AWS credentials.
    static final String SMTP_USERNAME =  PropUtil.get("stmpKey");  // SMTP用户名.
    static final String SMTP_PASSWORD =  PropUtil.get("smtpPassword");  // SMTP密码

    // 发件的host
    static final String HOST =  PropUtil.get("HOST");

    static final int PORT = 25;//端口

    public static void main(String[] args) throws Exception {
        System.out.println("FROM "+FROM+"======TO:"+TO);
        System.out.println("SUBJECT:"+SUBJECT+"\nBODY:"+BODY);
        // Create a Properties object to contain connection configuration information.
        Properties props = System.getProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.port", PORT);


        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getDefaultInstance(props);

        // Create a message with the specified information.
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FROM));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
        msg.setSubject(SUBJECT);
        msg.setContent(BODY, "text/plain");

        // Create a transport.
        Transport transport = session.getTransport();

        // Send the message.
        try {
            System.out.println("Attempting to send an email through the Amazon SES SMTP interface...");

            // Connect to Amazon SES using the SMTP username and password you specified above.
            transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);

            // Send the email.
            transport.sendMessage(msg, msg.getAllRecipients());
            System.out.println("Email sent!");
        } catch (Exception ex) {
            System.out.println("The email was not sent.");
            System.out.println("Error message: " + ex.getMessage());
        } finally {
            // Close and terminate the connection.
            transport.close();
        }
    }
}