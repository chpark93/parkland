package com.park.web.email.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.park.web.email.db.EmailDTO;
import com.park.web.email.service.EmailService;

@Controller
@RequestMapping("/email")
public class EmailController {
	
	@Inject   
	private EmailService emailservice;
	
	@RequestMapping(value="/writeEmail")
	public String writeEmail() {
		
		return "email/writeEmail";
	}
	
	@RequestMapping(value="/sendEmail")
	public String sendEmail(@ModelAttribute("emailDTO") EmailDTO emailDTO, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		try {
			emailservice.sendMail(emailDTO, response);
			model.addAttribute("message", "전송 완료");			
		}
		catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "전송 실패");	
		}
		
		return "redirect:/email/writeEmail";	
	}
}
