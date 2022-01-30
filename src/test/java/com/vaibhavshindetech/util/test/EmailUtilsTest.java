package com.vaibhavshindetech.util.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

import com.vaibhavshindetech.util.EmailUtils;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EmailUtilsTest {
//
//	private JavaMailSender javaMailSender;
//	private MimeMessage mimeMessage;
//	private EmailUtils emailUtilsMock;
//	
//
//	@BeforeAll
//	public void before() throws MessagingException {
//		mimeMessage = new MimeMessage(Mockito.mock(MimeMessage.class));
//		javaMailSender = mock(JavaMailSender.class);
//		when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
//		emailUtilsMock = new EmailUtils(javaMailSender);
//	}
//
//	@Test
//	public void sendEmail() {
//		String to = "vaibhavshinde@gmail.com";
//		String subject = "Registration Successful";
//		String body = "Hi,Vaibhav";
//		boolean sendEmail = emailUtilsMock.sendEmail(to, subject, body);
//		assertTrue(sendEmail);
//	}
}
