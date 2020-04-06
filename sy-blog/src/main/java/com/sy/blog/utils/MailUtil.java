package com.sy.blog.utils;

import com.alibaba.fastjson.JSONObject;
import com.sy.blog.utils.reids.RedisUtil;

import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	//fsabvzcowtunbjec
	/**
	 * 发送邮件
	 * @param to 给谁发
	 * @param text 发送内容
	 */
	//邮箱验证码
	private final static String FROM_EMAIL = "280337334@qq.com";
	private final static String HOST = "smtp.qq.com";//需要修改，126邮箱为smtp.126.com,163邮箱为smtp.163.com，QQ为smtp.qq.com
	private final static String PASSWORD = "fsabvzcowtunbjec";
	private final static String SMTP = "smtp";
	private static Properties properties = new Properties();
	private static Session session;

	public static void sendMail(String to,String code) throws Exception {

		properties.setProperty("mail.transport.protocol", "smtp");//电子邮箱协议

		properties.setProperty("mail.smtp.host", HOST); //邮箱服务器地址

		properties.setProperty("mail.smtp.auth", "true");

		session = Session.getInstance(properties);

		session.setDebug(true);//开启调试模式，可以追踪到邮件发送过程

		MimeMessage mimeMessage = new MimeMessage(session);

		String title = "欢迎访问SuuuY";
		mimeMessage.setSubject(title);//标题

		String context = "您的验证码为"+code+",五分钟有效。";
		mimeMessage.setContent(context, "text/html;charset=utf-8");//内容

		mimeMessage.setFrom(new InternetAddress(FROM_EMAIL));//发送人

		mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));//收件人

		mimeMessage.setSentDate(new Date());//发送时间

		mimeMessage.saveChanges();//保存修改

		Transport transport = session.getTransport(SMTP);

		transport.connect(FROM_EMAIL, PASSWORD);

		transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

		transport.close();

	}

//	public static boolean sendEmail(String emailaddress,String code){
//		try {
//			HtmlEmail email = new HtmlEmail();//不用更改
//			email.setHostName("smtp.qq.com");
//			email.setCharset("UTF-8");
//			email.addTo(emailaddress);// 收件地址
//			email.setFrom("596874930@qq.com", "SuY");//此处填邮箱地址和用户名,用户名可以任意填写
//
//			email.setAuthentication("596874930@qq.com", "xotkhhshccgxbejc");//此处填写邮箱地址和客户端授权码
//
//			email.setSubject("SuY");//此处填写邮件名，邮件名可任意填写
//			email.setMsg("尊敬的用户您好,您本次注册的验证码是:" + code);//此处填写邮件内容
//
//			email.send();
//			return true;
//		}
//		catch(Exception e){
//			e.printStackTrace();
//			return false;
//		}
//	}

	public static Boolean checkY(String to, String code){
		Boolean flag = false;
		String ck = RedisUtil.getSomething(to);
		System.out.println(code + "=================code");
		System.out.println(ck + "=================ck");
		System.out.println(ck != null);
		System.out.println(code.equals(ck));
		if(ck != null && code.equals(ck)) {//验证成功
			flag = true;
		}
		return flag;
	}

	public static JSONObject checkS(String to){
		JSONObject jsonObject = new JSONObject();
		String ckck = RedisUtil.getSomething(to);
		if(ckck == "" || ckck == null) {
			try {
				String ccc = String.valueOf((int)((Math.random()*9+1)*100000));
				RedisUtil.putSomething(to,5*60,ccc);
				sendMail(to,ccc);
			} catch (Exception e) {
				e.printStackTrace();
			}
			jsonObject.put("msg","验证码已发送");
			jsonObject.put("code",200);
		} else {
			jsonObject.put("msg","我已经发给你了，没收到就五分钟后再来");
			jsonObject.put("code",400);
		}
		return jsonObject;
	}
	public static boolean isEmail(String string) {
		if (string == null)
			return false;
		String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p;
		Matcher m;
		p = Pattern.compile(regEx1);
		m = p.matcher(string);
		if (m.matches())
			return true;
		else
			return false;
	}

	public static void main(String[] args) {
		try {
			sendMail("596874930@qq.com", String.valueOf((int)((Math.random()*9+1)*100000)));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
