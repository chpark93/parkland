package com.park.web.memberShip.exception;


public class AlreadyExistingEmailException extends RuntimeException{
	public AlreadyExistingEmailException(String message) {
		super(message);
	}
}
