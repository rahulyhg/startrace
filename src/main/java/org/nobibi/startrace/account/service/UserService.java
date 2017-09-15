package org.nobibi.startrace.account.service;

import java.util.Date;

import org.nobibi.startrace.account.bean.User;
import org.nobibi.startrace.account.persistence.dao.UserDao;
import org.nobibi.startrace.account.persistence.dao.UserMapper;
import org.nobibi.startrace.framework.plugins.log.Log;
import org.nobibi.startrace.framework.utils.MD5Helper;
import org.nobibi.startrace.framework.utils.UUIDGen;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private static final Logger LOGGER = Log.get(UserService.class);
	
	private MailSender mailSender;
	
	private SimpleMailMessage templateMessage;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserMapper userMapper;
	
	public void register(User user) {
		if (user == null) return;
		String uid = UUIDGen.getUUID();
		user.setId(uid);
		user.setPassword(MD5Helper.md5DoubleSalt(user.getPassword(), uid));
		user.setStatus(0);	
		user.setRegisterTime(new Date());
		userDao.save(user);
		
		if (user.getRegisterType() != null && user.getRegisterType().equals("mail")) {
			sendActMail(user);
		}
	}
	
	
	private void sendActMail(User user) {
		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
        StringBuilder text = new StringBuilder();
        text.append(user.getEmail()).append(", 您好:").append("\n")
        	.append("感谢您使用阿瞳目占星网，请在 24 小时内点击此链接以完成激活\n")
        	.append("<a href=\"https://astro.nobibi.org\" target=\"blank\">")
        	.append("https://astro.nobibi.org").append("</a>")
        	.append("(如果不能点击该链接地址，请复制并粘贴到浏览器的地址输入框)");
        
        //msg.setFrom("5103505@qq.com");
		msg.setTo(user.getEmail());
        msg.setSubject("占星网账号激活邮件");
        msg.setText(text.toString());
               
        try{
            this.mailSender.send(msg);
        } catch (MailException e) {
        	LOGGER.error("Send mail error", e);
        }
	}
	
	public void enableUser(String userId) {
		
	}
	
	public void disableUser(String userId) {
		
	}
	
	public User getUser(String key) {
		return userMapper.getUser(key);
		
	}
	
}
