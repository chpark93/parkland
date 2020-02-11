package com.park.web.email.service;

import com.park.web.email.db.EmailDTO;

public interface EmailService {

	void sendMail(EmailDTO emailDTO);

}
