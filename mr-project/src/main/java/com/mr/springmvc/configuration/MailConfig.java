//package com.mr.springmvc.configuration;
//
//import java.util.Properties;
//
//import javax.annotation.Resource;
//
//import org.apache.log4j.Logger;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.env.Environment;
//import org.springframework.mail.MailSender;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//@Configuration
//@PropertySource("classpath:application.properties")
//public class MailConfig {
//
//	@Resource
//	private Environment environment;
//	
//	private static final Logger logger = Logger.getLogger(MailConfig.class);
//	/**
//	 * The Java Mail sender.
//	 * It's not generally expected for mail sending to work in embedded mode.
//	 * Since this mail sender is always invoked asynchronously, this won't cause problems for the developer.
//	 */
//	@Bean
//	public MailSender mailSender() {
//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//		mailSender.setDefaultEncoding("UTF-8");
//		mailSender.setHost(environment.getProperty("mail.host"));
//		mailSender.setPort(environment.getProperty("mail.port", Integer.class, 25));
//		mailSender.setUsername(environment.getProperty("mail.username"));
//		mailSender.setPassword(environment.getProperty("mail.password"));
//		Properties properties = new Properties();
//		properties.put("mail.smtp.auth", environment.getProperty("mail.smtp.auth", Boolean.class, false));
//		properties.put("mail.smtp.starttls.enable", environment.getProperty("mail.smtp.starttls.enable", Boolean.class, false));
//		mailSender.setJavaMailProperties(properties);
//		
////		logger.info(environment.getProperty("mail.host"));
////		logger.info(environment.getProperty("mail.port"));
////		logger.info(environment.getProperty("mail.username"));
////		logger.info(environment.getProperty("mail.password"));
////		logger.info(environment.getProperty("mail.smtp.auth"));
////		logger.info(environment.getProperty("mail.smtp.starttls.enable"));
//		
//		return mailSender;
//	}
//	
//}