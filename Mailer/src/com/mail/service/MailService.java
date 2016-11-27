package com.mail.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {
	protected static final int	SMTP_PORT	= 25;
	protected static String SMTP_HOST = "QGBL-SMTPRELAY.QUINTILES.COM";
	protected String overrideTo;
	protected int maxAttempts = 5;
	

protected void createAndSendMessage(String from,String to, String subject, String body, String contentType) throws AddressException, MessagingException {
	Session session = getSession();
	MimeMessage message = createMessage(session, from, to, subject, body,contentType);
	sendMessage(message, session);
}
protected Session getSession() {
	Properties props = new Properties();
	props.put("mail.smtp.host", SMTP_HOST);
	props.put("mail.smtp.port", SMTP_PORT);
	Session session = Session.getInstance(props, null);
	return session;
}
protected MimeMessage createMessage(Session session, String from,String to, String subject, String body, String contentType)throws AddressException, MessagingException {
	if (overrideTo != null) to = overrideTo;
	MimeMessage message = new MimeMessage(session);
	message.setFrom(new InternetAddress(from));
	message.setRecipients(Message.RecipientType.TO, getRecievers(to).toArray(new InternetAddress[]{}));
	((MimeMessage) message).setSubject(subject);
	message.setSentDate(new Date());
	message.setContent(body, contentType);
	return message;
}

protected static List<InternetAddress> getRecievers(String recipients) throws AddressException {
	String[] addressStrings = recipients.split(",");
	List<InternetAddress> iaddresses = new ArrayList<InternetAddress>();
	for (String s : addressStrings) {
		iaddresses.add(new InternetAddress(s));
	}
	return iaddresses;
}
protected void sendMessage(MimeMessage message, Session session)throws MessagingException {
	Transport transport = session.getTransport("smtp");
	int i = 1;
	do {
		transport.connect();
		if (transport.isConnected()) {
			Transport.send(message);
			transport.close();
			break;
		}
		i++;
	} while (i < maxAttempts);
}
public void sendTextMail(String from, String to,String subject, String body) throws MailerException {
	try {
		StringBuilder sb = new StringBuilder();
		sb.append("From = ").append(from);
		sb.append("\n To = ").append(to);
		sb.append("\n Subject = ").append(subject).append("\n");
		System.out.println("sendTextMail from {}"+sb);
		createAndSendMessage(from, to, subject, body,"text/plain");
	} catch (AddressException e) {
		throw new MailerException(e);
	} catch (MessagingException e) {
		throw new MailerException(e);
	} finally {
	}
}
}
