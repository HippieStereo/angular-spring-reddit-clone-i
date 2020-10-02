package com.hippiestereo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hippiestereo.execptions.SpringRedditException;
import com.hippiestereo.model.NotificationEmail;

@Service
public class MailService {
	private final JavaMailSender mailSender;
	private final MailContentBuilder mailContentBuilder;
	private static final Logger LOG = LoggerFactory.getLogger(MailService.class);

	public MailService(JavaMailSender mailSender, MailContentBuilder mailContentBuilder) {
		this.mailSender = mailSender;
		this.mailContentBuilder = mailContentBuilder;
	}

	@Async
	public void sendmail(NotificationEmail notificationEmail) {
		MimeMessagePreparator messagePreparator =  mimeMessage -> {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
				messageHelper.setFrom("mail-svr@mail.com");
				messageHelper.setTo(notificationEmail.getRecipient());
				messageHelper.setSubject(notificationEmail.getSubject());
				messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
			};

		try {
			mailSender.send(messagePreparator);
			LOG.info("Activation email sent!!");
		} catch (MailException e) {
			LOG.error("Exception occurred when sending mail", e);
			throw new SpringRedditException("Exception occurred when sending mail to " 
					+ notificationEmail.getRecipient(), e);
		}
	}
}
