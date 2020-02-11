package com.park.web.memberShip.exception;

public class AlreadyExistingNickNameException extends RuntimeException {
	public AlreadyExistingNickNameException(String message) {
		super(message);
	}
}
