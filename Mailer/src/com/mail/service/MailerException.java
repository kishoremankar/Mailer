package com.mail.service;


public class MailerException extends Exception {

	private static final long serialVersionUID = -2935542310353632747L;

	public MailerException() {
		super();
	}

	public MailerException(String message, Throwable cause) {
		super(message, cause);
	}

	public MailerException(String message) {
		super(message);
	}

	public MailerException(Throwable cause) {
		super(cause);
	}

}
