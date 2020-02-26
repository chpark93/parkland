package com.park.web.email.service;

import java.io.PrintWriter;

import javax.inject.Inject;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.park.web.email.db.EmailDTO;

@Service
public class EmailServiceImpl implements EmailService {

	@Inject
	private JavaMailSender mailSender;
	
	@Override
	public void sendMail(EmailDTO emailDTO, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		try {
			
			MimeMessage msg = mailSender.createMimeMessage();
			
			//������
			msg.addRecipient(RecipientType.TO, new InternetAddress(emailDTO.getReceiveMail()));
			
			//�߽���
			msg.addFrom(new InternetAddress[] {(new InternetAddress(emailDTO.getSenderMail(), emailDTO.getSenderName()))});
		
			//subject
			msg.setSubject(emailDTO.getMailSubject(), "utf-8");
			//message
			msg.setText(emailDTO.getMailMessage(), "utf-8");
			
			//����
			mailSender.send(msg);
			
			out.print("<script> alert('���� ���� ���ۿ� �����߽��ϴ�.'); location.href='https://chparkland.com/park_project_1'; </script>");
			out.close();
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
