package feylix.vn.utils;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.InputStream;
import java.util.Properties;

/**
 * Lop tien ich gui email (dung cho OTP kich hoat tai khoan va OTP quen mat
 * khau). Cau hinh SMTP duoc doc tu file classpath: mail.properties.
 */
public class MailUtils {

	private static final Properties CONFIG = new Properties();

	static {
		try (InputStream is = MailUtils.class.getClassLoader().getResourceAsStream("mail.properties")) {
			if (is != null) {
				CONFIG.load(is);
			} else {
				System.err.println("[MailUtils] Khong tim thay file mail.properties trong classpath!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final String PURPOSE_REGISTER = "REGISTER";
	public static final String PURPOSE_RESET = "RESET";

	/**
	 * Gui mail chua ma OTP.
	 *
	 * @param toEmail email nguoi nhan
	 * @param otp     ma OTP 6 so
	 * @param purpose PURPOSE_REGISTER (kich hoat tai khoan) hoac PURPOSE_RESET
	 *                (quen mat khau)
	 */
	public static void sendOtpMail(String toEmail, String otp, String purpose) throws Exception {
		String host = CONFIG.getProperty("mail.smtp.host");
		String port = CONFIG.getProperty("mail.smtp.port");
		String username = CONFIG.getProperty("mail.username");
		String password = CONFIG.getProperty("mail.password");
		String fromName = CONFIG.getProperty("mail.from.name", "He thong Web App");

		if (username == null || password == null) {
			throw new IllegalStateException(
					"Chua cau hinh mail.username / mail.password trong mail.properties");
		}

		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		String subject;
		String content;

		if (PURPOSE_REGISTER.equals(purpose)) {
			subject = "[BaiTapLapTrinhWeb] Ma OTP kich hoat tai khoan";
			content = "<p>Xin chao,</p>"
					+ "<p>Ma OTP de kich hoat tai khoan cua ban la:</p>"
					+ "<h2 style=\"color:#0d6efd;\">" + otp + "</h2>"
					+ "<p>Ma co hieu luc trong <b>5 phut</b>. Vui long khong chia se ma nay cho bat ky ai.</p>";
		} else {
			subject = "[BaiTapLapTrinhWeb] Ma OTP dat lai mat khau";
			content = "<p>Xin chao,</p>"
					+ "<p>Ma OTP de dat lai mat khau cua ban la:</p>"
					+ "<h2 style=\"color:#dc3545;\">" + otp + "</h2>"
					+ "<p>Ma co hieu luc trong <b>5 phut</b>. Neu ban khong yeu cau dat lai mat khau, "
					+ "vui long bo qua email nay.</p>";
		}

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username, fromName));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
		message.setSubject(subject);
		message.setContent(content, "text/html; charset=UTF-8");

		Transport.send(message);
	}
}
