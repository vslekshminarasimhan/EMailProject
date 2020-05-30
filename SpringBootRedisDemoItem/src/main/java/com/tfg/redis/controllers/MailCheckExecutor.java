package com.tfg.redis.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sun.mail.pop3.POP3SSLStore;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;
import javax.mail.search.BodyTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import javax.mail.search.SubjectTerm;
import javax.mail.Message;
import javax.mail.Address;
import javax.mail.Multipart;
import javax.mail.BodyPart;
import javax.mail.Part;
import javax.mail.NoSuchProviderException;
import javax.mail.MessagingException;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * Class for implementation of email scheduled checking through @Scheduled annotation
 * which is available in Spring Framework. There is a logic for checking of new "unseen"
 * email messages, checking on letter's attachments and saveLetter messages into a database and
 * attachments in folder according profile's "path for attachments".
 */
@Component
public class MailCheckExecutor {



	private static final Logger LOGGER = LoggerFactory.getLogger(MailCheckExecutor.class);

    public MailCheckExecutor() {
    }

    /**
     * Method for checking profile's email with scheduled execution (every 5 second).
     * Note- 1000 for 1 second. 
     * @throws MessagingException 
     */
    @Scheduled(fixedRate = 300000)
    public void checkScheduleExecute() throws MessagingException {
       // LOGGER.info("Scheduled method for each profile check is running");
        System.out.println("Scheduled method for each profile check is running at."+System.currentTimeMillis());
      
try {
        connectAndCheckMail();
} catch(Exception e) {}
  
    }


    public void connectAndCheckMail() throws NoSuchProviderException, MessagingException, IOException{
    	
    	String host 	= "pop.gmail.com";  // if you use other email server remember to change this
		String user 	= "vslakshminarasimhan@gmail.com";
		String password = "forestroad";
		String protocol = "pop3";
		int port 		= 995;
		String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";   
	    
		Properties pop3Props = new Properties();  
	        pop3Props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
	        pop3Props.setProperty("mail.pop3.socketFactory.fallback", "false");
	        pop3Props.setProperty("mail.pop3.port",  "995");
	        pop3Props.setProperty("mail.pop3.socketFactory.port", "995");
	        
	     
	        URLName url = new URLName(protocol, host, port, "", user, password);
	        Session session = Session.getInstance(pop3Props, null);
	        Store store = new POP3SSLStore(session, url);
	        store.connect();
	        System.out.println("Connected!");
	        
	
			Folder inbox = store.getFolder("Inbox");
			inbox.open(Folder.READ_WRITE);
			
			
			
			int unreadMessageCount = store.getFolder("Inbox").getUnreadMessageCount();
			System.out.println("The value of unreadMessageCount"+unreadMessageCount);
			
			
			 // Message messages[] = inbox.getMessages();
			//Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
			
			SearchTerm term = new SearchTerm() {
			    public boolean match(Message message) {
			        try {
			            if (message.getSubject().contains("SpringBoot1")) {
			                return true;
			            }
			        } catch (MessagingException ex) {
			            ex.printStackTrace();
			        }
			        return false;
			    }
			};
		
		 SearchTerm subjectTerm = new SubjectTerm("SpringBoot1");

			
			
			Message[] foundMessages = inbox.search(subjectTerm);
			
			System.out.println("The length of foundMessages"+foundMessages.length);
			
			for ( Message message : foundMessages ) {
		          System.out.println("sendDate: " + message.getSentDate() + " subject:" + message.getSubject() );
		              message.setFlag(Flags.Flag.SEEN, true);
		        }
	  

    	
		}
    

    
    
    
}




