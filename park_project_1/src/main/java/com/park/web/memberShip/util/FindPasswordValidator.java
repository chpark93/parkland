package com.park.web.memberShip.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.park.web.memberShip.db.MemberShipVO;

//��й�ȣ ã�� ���� Ŭ����
public class FindPasswordValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;
	String regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$"; //�̸��� ���� ǥ����
	
	public FindPasswordValidator() {
		pattern = Pattern.compile(regexp);
		
	}
	
	@Override
	public boolean supports(Class<?> cls) {
		return MemberShipVO.class.isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		MemberShipVO memberShipVO = (MemberShipVO)target;
		
		//�̸��� ���� �˻�
		matcher = pattern.matcher(memberShipVO.getEmail());
		String email = memberShipVO.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.rejectValue("email", "emailRequired");
		}else if(!matcher.matches()) {
			errors.rejectValue("email", "emailBad");
		}
		
		
		
		
	}

}
