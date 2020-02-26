package com.park.web.email.service;

import javax.servlet.http.HttpServletResponse;

import com.park.web.email.db.EmailDTO;

public interface EmailService {

	void sendMail(EmailDTO emailDTO, HttpServletResponse response) throws Exception;

}
