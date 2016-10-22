package cn.chinaunicom.awarding.core.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import cn.chinaunicom.awarding.core.exception.AwardingCoreException;
import cn.chinaunicom.awarding.core.exception.AwardingCoreExceptionEnum;

public class AwardingEmailService {

	private JavaMailSender javaMailSender;

	private String systemEmail;

	public void sendMail(String to, String subject, String htmlText)
			throws AwardingCoreException {
		try {
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper msgHelper = new MimeMessageHelper(msg, true, "utf-8");
			
			msgHelper.setFrom(systemEmail);
			msgHelper.setTo(to);
			msgHelper.setSubject(subject);
			msgHelper.setText(htmlText, true);

			javaMailSender.send(msg);
		} catch (MessagingException e) {
			throw new AwardingCoreException(
					AwardingCoreExceptionEnum.FailToSendMail.toString(), e);
		}
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public String getSystemEmail() {
		return systemEmail;
	}

	public void setSystemEmail(String systemEmail) {
		this.systemEmail = systemEmail;
	}

}
