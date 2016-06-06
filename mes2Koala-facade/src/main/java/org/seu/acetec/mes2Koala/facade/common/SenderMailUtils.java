package org.seu.acetec.mes2Koala.facade.common;

/**
 * -----------------------------------------
 * @文件: SenderMailUtils.java
 * @作者: harlow
 * @邮箱: harlow.zhou@acetecsemi.net
 * @时间: 2015-5-19
 * @描述: 发送Email工具类
 * -----------------------------------------
 */

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

public class SenderMailUtils {
	private MailSender mailSender;
	private SimpleMailMessage simpleMailMessage;

	/**
	 * @方法名: sendMail
	 * @参数名：@param subject 邮件主题
	 * @参数名：@param content 邮件主题内容
	 * @参数名：@param to 收件人Email地址
	 * @描述语: 发送邮件
	 */
	@Async
	public void sendMail(String subject, String content, String to) {
		this.sendMail(subject, content, new String[] { to }, null, null);
	}

	/**
	 * @方法名: sendMail
	 * @参数名：@param subject 邮件主题
	 * @参数名：@param content 邮件主题内容
	 * @参数名：@param to 收件人Email地址
	 * @参数名：@param to 收件人Email地址
	 * @描述语: 发送邮件
	 */
	@Async
	public void sendMail(String subject, String content, String to, String[] cc) {
		this.sendMail(subject, content, new String[] { to }, cc, null);
	}

	/**
	 * @方法名: sendMail
	 * @参数名：@param subject 邮件主题
	 * @参数名：@param content 邮件主题内容
	 * @参数名：@param to 收件人Email地址
	 * @参数名：@param cc 抄送人Email地址
	 * @描述语: 发送邮件
	 */
	public void sendMail(String subject, String content, String[] to,
			String[] cc, String[] bcc) {
		simpleMailMessage.setSubject(subject); // 设置邮件主题
		simpleMailMessage.setTo(to); // 设定收件人
		simpleMailMessage.setCc(cc); // 设定抄送人
		simpleMailMessage.setBcc(bcc); // 设定抄送人
		simpleMailMessage.setText(content); // 设置邮件主题内容
		mailSender.send(simpleMailMessage); // 发送邮件
	}

	/**
	 * @方法名: sendMail
	 * @参数名：@param subject 邮件主题
	 * @参数名：@param content 邮件主题内容
	 * @参数名：@param to 收件人Email地址
	 * @描述语: 发送邮件
	 */
	public void sendMailHelper(String subject, String content, String to) {
		this.sendMailHelper(subject, content, new String[] { to }, null, null);
	}

	public void sendMailHelper(String subject, String content, String[] to) {
		this.sendMailHelper(subject, content, to, null, null);
	}

	/**
	 * @方法名: sendMailHelper
	 * @参数名：@param subject 邮件主题
	 * @参数名：@param content 邮件主题内容
	 * @参数名：@param to 收件人Email地址
	 * @参数名：@param to 收件人Email地址
	 * @描述语: 发送邮件
	 */
	public void sendMailHelper(String subject, String content, String to,
			String[] cc) {
		this.sendMailHelper(subject, content, new String[] { to }, cc, null);
	}

	public void sendMailHelper(String subject, String content, String[] to,
			String[] cc) {
		this.sendMailHelper(subject, content, to, cc, null);
	}

	/**
	 * @方法名: sendMailHelper
	 * @参数名：@param subject 邮件主题
	 * @参数名：@param content 邮件主题内容
	 * @参数名：@param to 收件人Email地址
	 * @参数名：@param cc 抄送人Email地址
	 * @描述语: 发送邮件
	 */
	public void sendMailHelper(String subject, String content, String[] to,
			String[] cc, String[] bcc) {
		this.sendMailHelper(subject, content, to, cc, bcc, null, null, false);
	}

	/**
	 * @方法名: sendMailHelper
	 * @参数名：@param subject 邮件主题
	 * @参数名：@param content 邮件主题内容
	 * @参数名：@param to 收件人Email地址
	 * @参数名：@param cc 抄送人Email地址
	 * @描述语: 发送邮件
	 */
	public void sendMailHelper(String subject, String content, String[] to,
			String[] cc, String[] bcc, String attachmentFilename,
			InputStreamSource inputStreamSource, boolean html) {
		JavaMailSender javaMailSender = (JavaMailSender) mailSender;
		MimeMessage mime = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mime, true, "utf-8");
			helper.setFrom(this.simpleMailMessage.getFrom());
			helper.setSubject(subject); // 设置邮件主题
			helper.setTo(to); // 设定收件人
			if (cc != null)
				helper.setCc(cc); // 设定抄送人
			if (bcc != null)
				helper.setBcc(bcc); // 设定抄送人
			if (attachmentFilename != null)
				helper.addAttachment(attachmentFilename, inputStreamSource);
			helper.setText(content, html); // 设置邮件主题内容
		} catch (MessagingException me) {
			me.printStackTrace();
		}
		javaMailSender.send(mime);
	}

	// Spring 依赖注入
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}

	// Spring 依赖注入
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}
}