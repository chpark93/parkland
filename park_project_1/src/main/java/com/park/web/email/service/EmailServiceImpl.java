package com.park.web.email.service;

import javax.inject.Inject;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.park.web.email.db.EmailDTO;

@Service
public class EmailServiceImpl implements EmailService {

	@Inject
	private JavaMailSender mailSender;
	
	@Override
	public void sendMail(EmailDTO emailDTO) {
		
		try {
			
			MimeMessage msg = mailSender.createMimeMessage();
			
			//수신자
			msg.addRecipient(RecipientType.TO, new InternetAddress(emailDTO.getReceiveMail()));
			
			//발신자
			msg.addFrom(new InternetAddress[] {(new InternetAddress(emailDTO.getSenderMail(), emailDTO.getSenderName()))});
		
			//subject
			msg.setSubject(emailDTO.getMailSubject(), "utf-8");
			//message
			msg.setText(emailDTO.getMailMessage(), "utf-8");
			
			//전송
			mailSender.send(msg);
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
