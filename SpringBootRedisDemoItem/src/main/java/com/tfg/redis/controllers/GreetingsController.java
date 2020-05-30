package com.tfg.redis.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sun.mail.pop3.POP3SSLStore;


@RestController
@RequestMapping("/api")
public class GreetingsController {
	
	
	@Autowired
	public JavaMailSender mailSender;
   
	@GetMapping("/greetings")
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(String name) {
        return "Greetings Controller. SpringBootRedisDemoItem for sending Mail. ";
    }
	
	
	
	 @RequestMapping(value = "/sendemail")
	   public void sendEmail() {
		 System.out.println("Send Email Service is being called.");
		 SimpleMailMessage message = new SimpleMailMessage();
		 message.setTo("vslakshminarasimhan@gmail.com");
		 message.setBcc("lakshminarasimhan_s@hcl.com");
		 message.setCc("laksh_minar@yahoo.com");
		 message.setSubject("SpringBoot1");
		 message.setText("Spring Boot Email Test");
	     mailSender.send(message);
	     System.out.println("Mail Sent Successfully. ");
	   }   
	 
	 
	 @RequestMapping(value="/sendemailhtml")
	 public void sendemailhtml() {
		 System.out.println("Send EmailHTML Service is being called.");
		 MimeMessagePreparator messagePreparator = mimeMessage -> {
	            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage) {{
	                setTo("vslakshminarasimhan@gmail.com");
	                setCc("laksminarasimhan_s@hcl.com");
	                setCc("laksh_minar@yahoo.com");
	                setSubject("SpringBoot2");
	                setText("SPring Boot Email Text", true);
	            }};
	        };

	        mailSender.send(messagePreparator);
		 
		 
	     System.out.println("MailHTML Sent Successfully. ");
	 }
	
	 
	 
	
	 
	
	
	
}
