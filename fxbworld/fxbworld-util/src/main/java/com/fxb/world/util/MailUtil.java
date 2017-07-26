package com.fxb.world.util;
/*package com.fxb.world.service.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.tsh.usercentre.vo.mail.EmailVo;

*//**
 * 
 * @author ds
 * 
 *//*
public class MailUtil {
	private JavaMailSender mailSender;

	// Spring 依赖注入
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	*//**
	 * 发送简单文本Email消息
	 * 
	 * @param emailVo
	 *//*
	public void sendEmailSimpleText(String recipient, String subject,
			String content, Date date) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(recipient);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(content);

		if (null == date) {
			date = new Date();
		}
		simpleMailMessage.setSentDate(date);

		mailSender.send(simpleMailMessage);
	}

	*//**
	 * html格式邮件发送
	 * 
	 * @param emailVo
	 * @throws UnsupportedEncodingException
	 *//*
	public void sendEmailHtml(EmailVo emailVo, Date date)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

		helper.setFrom(emailVo.getSender(), emailVo.getSenderName());
		helper.setValidateAddresses(true);
		if (StringUtils.isNotBlank(emailVo.getEmailContent())) {
			helper.setText(emailVo.getEmailContent(), true);
		}
		helper.setSubject(emailVo.getSubject());
		helper.setTo(emailVo.getReceivers());
		if (null == date) {
			date = new Date();
		}
		helper.setSentDate(date);

		mailSender.send(message);
	}

	*//**
	 * 带附件并且html格式邮件发送,HTML格式的消息
	 * 
	 * @param emailVo
	 * @param date
	 * @throws UnsupportedEncodingException
	 *//*
	public void sendEmailAttachedFileAndHtmlText(EmailVo emailVo, Date date,
			boolean isHtmlText) throws MessagingException,
			UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

		helper.setFrom(emailVo.getSender(), emailVo.getSenderName());
		// helper.setValidateAddresses(true);
		helper.setText(emailVo.getEmailContent(), isHtmlText);
		helper.setSubject(emailVo.getSubject());
		helper.setTo(emailVo.getReceivers());
		if (null == date) {
			date = new Date();
		}
		helper.setSentDate(date);

		for (File file : emailVo.getAttachFile()) {
			FileSystemResource fileSystemResource = new FileSystemResource(file);
			helper.addAttachment(file.getName(), fileSystemResource);
		}
		mailSender.send(message);
	}
}
*/