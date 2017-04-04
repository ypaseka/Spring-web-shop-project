package com.shop.others.email;

import java.util.Date;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import com.shop.configuration.ApplicationConfig;
import com.shop.data.tables.Users;
import com.shop.others.RepositoriesAccess;

public class SendEmailUserAccount {

	public static void sendEmailWithOrder(String text, String eMail, HttpServletRequest request) {
		try {
			Session session = EmailActions.authorizeWebShopEmail();

			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(ApplicationConfig.SHOP_EMAIL));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(eMail, false));
			msg.setSubject("Shop order");
			msg.setText(text);
			msg.setSentDate(new Date());
			Transport.send(msg);
		} catch (MessagingException e) {
			System.out.println("Error : " + e);
		}
	}

	public static boolean sendEmailWithNewPassswordOrEmail(String email, String newPassword, Model model, HttpServletRequest request) {
		Users user1 = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Users user = RepositoriesAccess.usersRepository.findByLogin(user1.getLogin());

		boolean success = false;
		try {
			Session session = EmailActions.authorizeWebShopEmail();

			if (newPassword != null && email.equals("")) {
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(ApplicationConfig.SHOP_EMAIL));
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.geteMail(), false));
				
				msg.setSubject("New password");
				msg.setText("New password : " + newPassword);
				msg.setSentDate(new Date());
				Transport.send(msg);

				user.setPassword(newPassword);
				RepositoriesAccess.usersRepository.save(user);
				success = true;
			} else {
				String code = Long.toHexString(Double.doubleToLongBits(Math.random()));
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(ApplicationConfig.SHOP_EMAIL));
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.geteMail(), false));
				
				msg.setSubject("Change Email");
				msg.setText("Your code : " + code);
				msg.setSentDate(new Date());
				Transport.send(msg);

				request.getSession().setAttribute("code", code);
				success = true;
			}

		} catch (MessagingException e) {
			System.out.println("Error : " + e);
		}
		return success;
	}
}