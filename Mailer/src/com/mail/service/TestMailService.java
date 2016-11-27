package com.mail.service;

public class TestMailService {
public static void main(String[] args) {
	MailService service = new MailService();
	try {
		service.sendTextMail("kishore.mankar@quintiles.com", "kishore.mankar@quintiles.com", "Mail Service", "Testing Mail Service");
	} catch (MailerException e) {
		e.printStackTrace();
	}
	System.out.println("Message sent successfully");
}
}
