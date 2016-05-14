package com.mr.springmvc.common;

import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.mail.MailSender;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

//import com.mr.springmvc.configuration.MailConfig;

@Service
public class SendMail {

	// @Autowired
	// private MailSender mailSender;

	// public void setMailSender(MailSender mailSender) {
	// this.mailSender = mailSender;
	// }
	// @Resource(name = "mailSender")
	// @Autowired
	// private MailSender mailSender;
	// @Autowired
	// private SimpleMailMessage templateMessage;

//	public void send() {
//		try {
//
////			AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
////			ctx.register(MailConfig.class);
////			ctx.refresh();
////			MailSender mailSender = ctx.getBean(MailSender.class);
////
////			SimpleMailMessage mail = new SimpleMailMessage();
////			// Message message = new MimeMessage;
////			String toAddr = "khanhnn@itvn-outsourcing.com";
////			String fromAddr = "khanhnn04013@gmail.com";
////
////			// email subject
////			String subject = "Create restaurant successfull!";
////
////			// email body
////			String body = "Welcome to new project!";
////			mail.setFrom(fromAddr);
////			mail.setTo(toAddr);
////			mail.setSubject(subject);
////			mail.setText(body);
////			mailSender.send(mail);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
	
	public void send() {
		final String username = "cuongnl.iteal@gmail.com";
		final String password = "megadragon";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		// Session session = Session.getInstance(props, new
		// GMailAuthenticator(username, password));
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("cuongnl.iteal@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("khanhnn@itvn-outsourcing.com"));
			message.setSubject("Create restaurant successfull!");
			message.setText("Dear Mr. Khanh," + "\n\n Welcome to new project!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
